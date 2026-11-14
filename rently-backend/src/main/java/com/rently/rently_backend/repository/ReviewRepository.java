package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Page<Review> findByListingIdAndIsBlindRevealedTrueAndModerationStatusOrderByCreatedAtDesc(
            String listingId, String moderationStatus, Pageable pageable);
    boolean existsByBookingIdAndReviewerId(String bookingId, String reviewerId);
}

