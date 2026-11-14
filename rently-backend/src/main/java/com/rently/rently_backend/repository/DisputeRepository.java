package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Dispute;
import com.rently.rently_backend.model.enums.DisputeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisputeRepository extends MongoRepository<Dispute, String> {
    Optional<Dispute> findByDisputeNumber(String disputeNumber);
    Optional<Dispute> findByBookingId(String bookingId);
    Page<Dispute> findByStatusOrderByCreatedAtAsc(DisputeStatus status, Pageable pageable);
}

