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
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
@CompoundIndex(name = "conv_created_idx", def = "{'conversationId': 1, 'createdAt': 1}")
public class Message {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String conversationId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String senderId;

    private String messageType;
    private String text;
    private List<String> attachments;
    private Map<String, Object> structuredPayload;

    @Builder.Default
    private Boolean flaggedForOffPlatformLeak = false;

    @CreatedDate
    private Instant createdAt;
}

