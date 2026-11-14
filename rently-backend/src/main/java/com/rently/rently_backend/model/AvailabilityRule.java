package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "availability_rules")
@CompoundIndex(name = "provider_listing_avail_idx", def = "{'providerUserId': 1, 'listingId': 1}")
public class AvailabilityRule {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    @Builder.Default
    private String timezone = "Asia/Colombo";

    @Builder.Default
    private Boolean unifiedCalendarSync = true;

    private List<DaySchedule> weeklySchedule;

    @Builder.Default
    private Integer minAdvanceNoticeMinutes = 60;

    @Builder.Default
    private Integer maxAdvanceBookingDays = 90;

    @LastModifiedDate
    private Instant updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {
        private Integer dayOfWeek;
        private Boolean isOpen;
        private List<TimeSlot> slots;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSlot {
        private String startTime;
        private String endTime;
    }
}

