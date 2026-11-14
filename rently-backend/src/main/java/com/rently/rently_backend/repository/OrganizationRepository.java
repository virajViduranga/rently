package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Optional<Organization> findBySlug(String slug);
    List<Organization> findByOwnerUserId(String ownerUserId);
}

