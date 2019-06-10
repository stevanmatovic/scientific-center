package com.stevanmatovic.elastic.scientificcentral.repository;

import com.stevanmatovic.elastic.scientificcentral.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JournalRepository  extends JpaRepository<Journal,Long>{

    Journal findByTitle(String title);

}
