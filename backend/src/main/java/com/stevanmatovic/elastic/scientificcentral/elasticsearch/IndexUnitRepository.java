package com.stevanmatovic.elastic.scientificcentral.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface IndexUnitRepository extends ElasticsearchRepository<IndexUnit, String> {

  public List<IndexUnit> findByTitle(String title);
}
