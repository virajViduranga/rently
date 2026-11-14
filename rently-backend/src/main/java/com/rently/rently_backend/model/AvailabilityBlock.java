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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "availability_blocks")
@CompoundIndex(name = "listing_time_block_idx", def = "{'listingId': 1, 'startAt': 1, 'endAt': 1}")
public class AvailabilityBlock {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    private String blockType;

    @Builder.Default
    private Integer quantityBlocked = 1;

    private Instant startAt;
    private Instant endAt;

    @Indexed(expireAfterSeconds = 0)
    private Instant expiresAt;

    private String notes;

    @CreatedDate
    private Instant createdAt;
}

