package com.rently.rently_backend.model;

import com.rently.rently_backend.model.enums.ListingType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
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
@Document(collection = "broadcast_requests")
public class BroadcastRequest {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    private ListingType requestType;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String categoryId;

    private String title;
    private String description;
    private List<String> mediaUrls;
    private Instant neededFromAt;
    private Instant neededUntilAt;
    private Double maxBudget;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private Double radiusKm;

    @Indexed
    @Builder.Default
    private String status = "OPEN";

    @Field(targetType = FieldType.OBJECT_ID)
    private String fulfilledByBookingId;

    private Instant expiresAt;

    @CreatedDate
    private Instant createdAt;
}

