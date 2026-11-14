package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
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
@Document(collection = "reviews")
@CompoundIndex(name = "booking_direction_unique_idx", def = "{'bookingId': 1, 'reviewDirection': 1}", unique = true)
public class Review {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String reviewerId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String revieweeId;

    private String reviewDirection; // CUSTOMER_TO_PROVIDER, PROVIDER_TO_CUSTOMER
    private Integer rating;
    private SubRatings subRatings;
    private String comment;
    private List<String> photoUrls;
    private ProviderReply providerReply;

    @Builder.Default
    private Boolean isBlindRevealed = false;

    private Instant revealAfterAt;

    @Builder.Default
    private String moderationStatus = "VISIBLE";

    @CreatedDate
    private Instant createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubRatings {
        private Integer itemConditionOrWorkQuality;
        private Integer punctuality;
        private Integer communication;
        private Integer itemCareByCustomer;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProviderReply {
        private String text;
        private Instant repliedAt;
    }
}

