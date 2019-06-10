package com.stevanmatovic.elastic.scientificcentral.service;

import com.stevanmatovic.elastic.scientificcentral.model.Paper;
import com.stevanmatovic.elastic.scientificcentral.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaperService {

  private PaperRepository paperRepository;

  @Autowired
  public PaperService(PaperRepository paperRepository) {
    this.paperRepository = paperRepository;
  }

  public void save(Paper paper) {
    this.paperRepository.save(paper);
  }

  public Paper getById(Long id) {
    return this.paperRepository.getOne(id);
  }

}
