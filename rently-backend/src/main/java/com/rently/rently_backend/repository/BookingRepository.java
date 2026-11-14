package com.rently.rently_backend.repository;

import com.rently.rently_backend.model.Booking;
import com.rently.rently_backend.model.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    Optional<Booking> findByBookingNumber(String bookingNumber);
    Page<Booking> findByCustomerIdOrderByCreatedAtDesc(String customerId, Pageable pageable);
    Page<Booking> findByCustomerIdAndStatusInOrderByCreatedAtDesc(String customerId, List<BookingStatus> statuses, Pageable pageable);
    Page<Booking> findByProviderUserIdOrderByCreatedAtDesc(String providerUserId, Pageable pageable);
    Page<Booking> findByProviderUserIdAndStatusInOrderByCreatedAtDesc(String providerUserId, List<BookingStatus> statuses, Pageable pageable);
    boolean existsByListingIdAndStatusIn(String listingId, List<BookingStatus> activeStatuses);
    List<Booking> findByStatusAndScheduleInspectionWindowExpiresAtBefore(BookingStatus status, Instant now);
}

