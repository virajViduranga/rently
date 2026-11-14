package com.rently.rently_backend.model;

import com.rently.rently_backend.model.enums.InspectionStage;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
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
@Document(collection = "handover_inspections")
@CompoundIndex(name = "booking_stage_inspection_idx", def = "{'bookingId': 1, 'stage': 1}")
public class HandoverInspection {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    private InspectionStage stage;

    @Field(targetType = FieldType.OBJECT_ID)
    private String submittedByUserId;

    private String submittedByRole;
    private Boolean workingTestPassed;
    private List<AccessoryCheck> accessoriesChecklist;
    private String preExistingDamageNotes;

    @Builder.Default
    private Boolean newDamageClaimed = false;

    private String newDamageDescription;
    private Double estimatedRepairCost;
    private List<MediaProofItem> mediaProof;
    private CounterpartyAcknowledgement counterpartyAcknowledgement;

    @CreatedDate
    private Instant createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessoryCheck {
        private String accessoryName;
        private Boolean isPresent;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MediaProofItem {
        private String url;
        private String mediaType;
        private String sha256Hash;
        private Instant capturedAt;
        private GeoJsonPoint gpsCoordinates;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CounterpartyAcknowledgement {
        @Builder.Default
        private String status = "PENDING";
        @Field(targetType = FieldType.OBJECT_ID)
        private String acknowledgedByUserId;
        private Instant acknowledgedAt;
    }
}

