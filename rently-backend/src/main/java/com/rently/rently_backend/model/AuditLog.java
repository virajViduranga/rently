package com.rently.rently_backend.model;

import com.rently.rently_backend.model.enums.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
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
@Document(collection = "audit_logs")
@CompoundIndex(name = "target_audit_idx", def = "{'targetCollection': 1, 'targetId': 1, 'createdAt': -1}")
public class AuditLog {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String actorUserId;

    private Role actorRole;

    @Indexed
    private String action;

    private String targetCollection;

    @Field(targetType = FieldType.OBJECT_ID)
    private String targetId;

    private String reasonJustification;
    private Map<String, Object> beforeSnapshot;
    private Map<String, Object> afterSnapshot;
    private String ipAddress;
    private String userAgent;
    private Boolean mfaVerifiedForAction;

    @CreatedDate
    private Instant createdAt;
}

