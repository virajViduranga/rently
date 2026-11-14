package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Listing;
import com.rently.rently_backend.model.enums.ListingType;
import com.rently.rently_backend.model.enums.PublicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends MongoRepository<Listing, String> {
    Page<Listing> findByLocationGeoNearAndListingTypeAndPublicationStatus(
            Point location, Distance maxDistance, ListingType listingType, PublicationStatus status, Pageable pageable);
    Page<Listing> findByProviderUserIdOrderByCreatedAtDesc(String providerUserId, Pageable pageable);
    List<Listing> findByProviderUserIdAndPublicationStatusNot(String providerUserId, PublicationStatus excludedStatus);
}

