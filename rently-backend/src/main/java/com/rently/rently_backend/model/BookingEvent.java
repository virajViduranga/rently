package com.rently.rently_backend.model;

import com.rently.rently_backend.model.enums.BookingStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking_events")
public class BookingEvent {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    private String fromStatus;
    private BookingStatus toStatus;

    @Field(targetType = FieldType.OBJECT_ID)
    private String actorUserId;

    private String actorType;
    private String reason;
    private GeoJsonPoint geoPoint;
    private Map<String, Object> metadata;

    @CreatedDate
    private Instant createdAt;
}

