package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
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
@Document(collection = "reports")
@CompoundIndex(name = "status_priority_report_idx", def = "{'status': 1, 'riskPriority': -1, 'createdAt': 1}")
public class Report {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String reporterUserId;

    private String targetType;

    @Field(targetType = FieldType.OBJECT_ID)
    private String targetId;

    private String reasonCategory;
    private String description;
    private List<String> evidenceUrls;
    private String riskPriority;

    @Builder.Default
    private String status = "OPEN";

    @Field(targetType = FieldType.OBJECT_ID)
    private String assignedModeratorId;

    private String resolutionNotes;
    private Instant resolvedAt;

    @CreatedDate
    private Instant createdAt;
}

