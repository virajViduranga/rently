package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.EscrowLedger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscrowLedgerRepository extends MongoRepository<EscrowLedger, String> {
    List<EscrowLedger> findByBookingIdOrderByCreatedAtAsc(String bookingId);
}

