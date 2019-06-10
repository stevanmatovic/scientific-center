package com.stevanmatovic.elastic.scientificcentral.repository;

import com.stevanmatovic.elastic.scientificcentral.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}
