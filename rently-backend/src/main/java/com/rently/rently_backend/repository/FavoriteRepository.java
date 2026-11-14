package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    List<Favorite> findByUserIdOrderByCreatedAtDesc(String userId);
    boolean existsByUserIdAndListingId(String userId, String listingId);
    void deleteByUserIdAndListingId(String userId, String listingId);
}

