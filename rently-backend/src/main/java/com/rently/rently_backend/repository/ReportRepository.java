package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Page<Report> findByStatusOrderByRiskPriorityDescCreatedAtAsc(String status, Pageable pageable);
}

