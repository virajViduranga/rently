package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.AvailabilityRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRuleRepository extends MongoRepository<AvailabilityRule, String> {
    Optional<AvailabilityRule> findByProviderUserIdAndListingId(String providerUserId, String listingId);
    List<AvailabilityRule> findByProviderUserId(String providerUserId);
}

