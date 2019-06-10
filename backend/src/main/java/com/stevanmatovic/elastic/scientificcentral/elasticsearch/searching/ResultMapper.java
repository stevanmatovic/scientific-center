package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;

public class ResultMapper implements SearchResultMapper {

  RequiredHighlight requiredHighlight;

  public ResultMapper(RequiredHighlight requiredHighlight) {
    this.requiredHighlight = requiredHighlight;
  }

  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
    List<IndexUnit> chunk = new ArrayList<IndexUnit>();
    for (SearchHit searchHit : response.getHits()) {
      if (response.getHits().getHits().length <= 0) {
        return null;
      }
      IndexUnit data = new IndexUnit();
      data.setTitle((String) searchHit.getSourceAsMap().get("title"));
      data.setAuthor((String) searchHit.getSourceAsMap().get("author"));
      data.setKeywords((String) searchHit.getSourceAsMap().get("keywords"));
      data.setFilename(searchHit.getId());
      HighlightField hf = searchHit.getHighlightFields().get(requiredHighlight.getFieldName());
      String highlight = hf.fragments()[0].toString();
      for (int i = 0; i < hf.fragments().length; i++) {
        if (i == 3)
          break;
        highlight += hf.fragments()[i].toString() + "...\t";
      }
      data.setHighlight(highlight);
      chunk.add(data);
    }
    if (chunk.size() > 0) {
      return new AggregatedPageImpl<T>((List<T>) chunk);
    }
    return null;
  }
}
