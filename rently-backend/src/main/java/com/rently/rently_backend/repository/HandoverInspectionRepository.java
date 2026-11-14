package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.HandoverInspection;
import com.rently.rently_backend.model.enums.InspectionStage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HandoverInspectionRepository extends MongoRepository<HandoverInspection, String> {
    List<HandoverInspection> findByBookingIdOrderByCreatedAtAsc(String bookingId);
    Optional<HandoverInspection> findFirstByBookingIdAndStageOrderByCreatedAtDesc(String bookingId, InspectionStage stage);
}

