package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_sessions")
public class UserSession {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;

    @Indexed(unique = true)
    private String refreshTokenHash;

    private DeviceInfo deviceInfo;

    @Builder.Default
    private Boolean isRevoked = false;

    private String revokedReason;
    private Instant lastActiveAt;

    @Indexed(expireAfterSeconds = 0)
    private Instant expiresAt;

    @CreatedDate
    private Instant createdAt;

    public boolean isActiveSession() {
        return !Boolean.TRUE.equals(isRevoked) && expiresAt != null && Instant.now().isBefore(expiresAt);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceInfo {
        private String deviceId;
        private String platform;
        private String userAgent;
        private String ipAddress;
        private String fcmPushToken;
    }
}

