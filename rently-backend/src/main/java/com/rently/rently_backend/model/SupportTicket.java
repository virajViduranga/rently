package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "support_tickets")
public class SupportTicket extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String ticketNumber;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String requesterUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    private String category;
    private String priority;
    private String subject;
    private String description;
    private GeoJsonPoint liveSosGps;

    @Field(targetType = FieldType.OBJECT_ID)
    private String assignedAgentId;

    @Builder.Default
    private List<InternalNote> internalNotes = new ArrayList<>();

    private String escalatedToRole;

    @Indexed
    @Builder.Default
    private String status = "OPEN";

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InternalNote {
        @Field(targetType = FieldType.OBJECT_ID)
        private String authorStaffId;
        private String note;
        private Instant createdAt;
    }
}

