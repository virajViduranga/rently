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
@Document(collection = "escrow_ledgers")
public class EscrowLedger {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String paymentId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String refundId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String payoutId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String disputeId;

    private String entryType;
    private String debitAccount;
    private String creditAccount;
    private Double amount;

    @Builder.Default
    private String currency = "LKR";

    @Field(targetType = FieldType.OBJECT_ID)
    private String createdByUserId;

    @CreatedDate
    private Instant createdAt;
}

