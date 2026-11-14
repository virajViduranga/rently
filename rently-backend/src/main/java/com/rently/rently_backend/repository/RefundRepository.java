package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Refund;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepository extends MongoRepository<Refund, String> {
    List<Refund> findByBookingId(String bookingId);
}

