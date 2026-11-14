package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
    Page<AuditLog> findByTargetCollectionAndTargetIdOrderByCreatedAtDesc(
            String targetCollection, String targetId, Pageable pageable);
}

