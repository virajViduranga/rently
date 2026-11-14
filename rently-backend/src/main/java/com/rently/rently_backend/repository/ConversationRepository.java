package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByBookingId(String bookingId);
    List<Conversation> findByParticipantsUserIdOrderByLastMessageAtDesc(String userId);
}

