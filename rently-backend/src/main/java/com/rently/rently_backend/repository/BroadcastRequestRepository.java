package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.BroadcastRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BroadcastRequestRepository extends MongoRepository<BroadcastRequest, String> {
    List<BroadcastRequest> findByLocationNearAndStatusAndCategoryId(Point point, Distance distance, String status, String categoryId);
}

