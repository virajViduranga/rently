package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.OrganizationMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationMemberRepository extends MongoRepository<OrganizationMember, String> {
    Optional<OrganizationMember> findByOrganizationIdAndUserId(String organizationId, String userId);
    List<OrganizationMember> findByUserIdAndStatus(String userId, String status);
}

