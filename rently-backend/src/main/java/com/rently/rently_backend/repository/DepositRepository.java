package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Deposit;
import com.rently.rently_backend.model.enums.DepositStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends MongoRepository<Deposit, String> {
    Optional<Deposit> findByBookingId(String bookingId);
    List<Deposit> findByStatusAndAutoReleaseScheduledAtBefore(DepositStatus status, Instant now);
}

