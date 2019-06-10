package com.stevanmatovic.elastic.scientificcentral.elasticsearch.searching;

import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnit;
import com.stevanmatovic.elastic.scientificcentral.elasticsearch.IndexUnitRepository;
import org.apache.lucene.queryparser.classic.ParseException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultRetriever {

  private IndexUnitRepository repository;

  private ElasticsearchTemplate template;

  @Autowired
  public ResultRetriever(IndexUnitRepository repository, ElasticsearchTemplate template) {
    this.repository = repository;
    this.template = template;
  }

  public List<IndexUnit> getResults(SimpleQuery simpleQuery, SearchType searchType) throws ParseException {

    if (simpleQuery.getField() == null || simpleQuery.getValue() == null)
      return null;

    org.elasticsearch.index.query.QueryBuilder query = QueryBuilder
        .buildQuery(searchType, simpleQuery.getField(), simpleQuery.getValue());

    RequiredHighlight highlight = new RequiredHighlight(simpleQuery.getField(), simpleQuery.getValue().toLowerCase());

    SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query)
        .withHighlightFields(new HighlightBuilder.Field(highlight.getFieldName())).build();

    Page<IndexUnit> searchResponse = template.queryForPage(searchQuery, IndexUnit.class, new ResultMapper(highlight));

    List<IndexUnit> results = new ArrayList<>();
    if (searchResponse == null)
      return results;
    for (IndexUnit indexUnit : searchResponse) {
      results.add(indexUnit);
    }
    return results;
  }

  public List<IndexUnit> getResults(AdvancedQuery advancedQuery) throws ParseException {

    org.elasticsearch.index.query.QueryBuilder query1 = QueryBuilder
        .buildQuery(SearchType.regular, advancedQuery.getField1(), advancedQuery.getValue1());
    org.elasticsearch.index.query.QueryBuilder query2 = QueryBuilder
        .buildQuery(SearchType.regular, advancedQuery.getField2(), advancedQuery.getValue2());

    BoolQueryBuilder builder = QueryBuilders.boolQuery();
    if (advancedQuery.getOperation().equalsIgnoreCase("AND")) {
      builder.must(query1);
      builder.must(query2);
    } else if (advancedQuery.getOperation().equalsIgnoreCase("OR")) {
      builder.should(query1);
      builder.should(query2);
    } else if (advancedQuery.getOperation().equalsIgnoreCase("NOT")) {
      builder.must(query1);
      builder.mustNot(query2);
    }

    RequiredHighlight highlight = new RequiredHighlight(advancedQuery.getField1(),
        advancedQuery.getValue1().toLowerCase());

    SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder)
        .withHighlightFields(new HighlightBuilder.Field(highlight.getFieldName())).build();

    Page<IndexUnit> searchResponse = template.queryForPage(searchQuery, IndexUnit.class, new ResultMapper(highlight));

    List<IndexUnit> results = new ArrayList<>();
    if (searchResponse == null)
      return results;
    for (IndexUnit indexUnit : searchResponse) {
      results.add(indexUnit);
    }
    return results;
  }

}
