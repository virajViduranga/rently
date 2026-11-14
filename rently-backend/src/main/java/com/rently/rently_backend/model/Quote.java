package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "quotes")
public class Quote {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String broadcastRequestId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    @Builder.Default
    private Integer revisionNumber = 1;

    private List<String> taskPhotosFromCustomer;
    private String scopeSummary;
    private List<QuoteLineItem> lineItems;

    @Builder.Default
    private String currency = "LKR";
    private Double totalAmount;
    private Integer estimatedDurationMinutes;
    private Instant proposedStartAt;

    @Builder.Default
    private String status = "REQUESTED"; // REQUESTED, SENT, ACCEPTED, DECLINED_PRICE_MISMATCH, SUPERSEDED, EXPIRED

    @Indexed(expireAfterSeconds = 2592000)
    private Instant expiresAt;

    @CreatedDate
    private Instant createdAt;

    public void recalculateTotal() {
        if (lineItems == null || lineItems.isEmpty()) {
            this.totalAmount = 0.0;
            return;
        }
        this.totalAmount = lineItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuoteLineItem {
        private String label;
        private String category;
        private Double quantity;
        private Double unitPrice;
        private Double total;
    }
}

