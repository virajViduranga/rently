package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.VerificationRecord;
import com.rently.rently_backend.model.enums.VerificationStatus;
import com.rently.rently_backend.model.enums.VerificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationRecordRepository extends MongoRepository<VerificationRecord, String> {
    List<VerificationRecord> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<VerificationRecord> findFirstByUserIdAndVerificationTypeAndStatusOrderByCreatedAtDesc(
            String userId, VerificationType type, VerificationStatus status);
    Page<VerificationRecord> findByStatusOrderByCreatedAtAsc(VerificationStatus status, Pageable pageable);
}

