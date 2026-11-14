package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, String> {
    List<Quote> findByCustomerIdOrderByCreatedAtDesc(String customerId);
    List<Quote> findByProviderUserIdOrderByCreatedAtDesc(String providerUserId);
}

