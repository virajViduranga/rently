package com.rently.rently_backend.model;

import com.rently.rently_backend.model.enums.EscrowState;
import com.rently.rently_backend.model.enums.PaymentStatus;
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
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    private String paymentPurpose;
    private Double amount;
    private Double rentalOrServicePortion;
    private Double depositPortion;

    @Builder.Default
    private String currency = "LKR";

    private String paymentMethod;
    private String pspName;
    private String pspTransactionId;

    @Indexed(unique = true)
    private String pspIdempotencyKey;

    private String cardMaskedLast4;

    @Builder.Default
    private EscrowState escrowState = EscrowState.HELD_IN_ESCROW;

    @Builder.Default
    private PaymentStatus status = PaymentStatus.INITIATED;

    private String failureReason;
    private Instant capturedAt;

    @CreatedDate
    private Instant createdAt;
}

