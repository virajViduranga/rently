package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.AvailabilityBlock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AvailabilityBlockRepository extends MongoRepository<AvailabilityBlock, String> {
    @Query("{ 'listingId': ?0, 'startAt': { $lt: ?2 }, 'endAt': { $gt: ?1 } }")
    List<AvailabilityBlock> findOverlappingBlocks(String listingId, Instant requestedStart, Instant requestedEnd);
    List<AvailabilityBlock> findByProviderUserIdAndEndAtGreaterThanEqual(String providerUserId, Instant from);
}

