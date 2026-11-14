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
@Document(collection = "refunds")
public class Refund {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String paymentId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String depositId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String disputeId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    private Double amount;
    @Builder.Default
    private String currency = "LKR";
    private String reason;
    private String initiatedByRole;

    @Field(targetType = FieldType.OBJECT_ID)
    private String initiatedByUserId;

    private String pspRefundId;

    @Builder.Default
    private String status = "PENDING";

    private Instant processedAt;

    @CreatedDate
    private Instant createdAt;
}

