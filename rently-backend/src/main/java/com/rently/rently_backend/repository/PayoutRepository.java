package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Payout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayoutRepository extends MongoRepository<Payout, String> {
    Page<Payout> findByProviderUserIdOrderByCreatedAtDesc(String providerUserId, Pageable pageable);
}

