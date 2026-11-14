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
@Document(collection = "notifications")
@CompoundIndex(name = "user_unread_notif_idx", def = "{'userId': 1, 'isRead': 1, 'createdAt': -1}")
public class Notification {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;

    private String eventType;
    private String title;
    private String body;
    private String deepLinkUrl;
    private List<String> channelsSent;

    @Builder.Default
    private Boolean isRead = false;

    private Instant readAt;

    @Indexed(expireAfterSeconds = 0)
    private Instant expiresAt;

    @CreatedDate
    private Instant createdAt;
}

