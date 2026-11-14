package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.UserSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends MongoRepository<UserSession, String> {
    Optional<UserSession> findByRefreshTokenHashAndIsRevokedFalse(String refreshTokenHash);
    List<UserSession> findByUserIdAndIsRevokedFalse(String userId);
}

