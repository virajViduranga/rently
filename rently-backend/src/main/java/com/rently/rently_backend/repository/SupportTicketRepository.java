package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.SupportTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupportTicketRepository extends MongoRepository<SupportTicket, String> {
    Optional<SupportTicket> findByTicketNumber(String ticketNumber);
    Page<SupportTicket> findByRequesterUserIdOrderByCreatedAtDesc(String requesterUserId, Pageable pageable);
    Page<SupportTicket> findByStatusOrderByPriorityDescCreatedAtAsc(String status, Pageable pageable);
}

