package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.DepositStatus;
import lombok.*;
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
@EqualsAndHashCode(callSuper = true)
@Document(collection = "deposits")
public class Deposit extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String paymentId;

    @Builder.Default
    private String currency = "LKR";

    private Double heldAmount;
    @Builder.Default
    private Double frozenDisputedAmount = 0.0;
    @Builder.Default
    private Double deductedForLateFee = 0.0;
    @Builder.Default
    private Double deductedForDamage = 0.0;
    @Builder.Default
    private Double refundedToCustomerAmount = 0.0;

    @Indexed
    @Builder.Default
    private DepositStatus status = DepositStatus.ACTIVE_IN_ESCROW;

    @Indexed
    private Instant autoReleaseScheduledAt;

    @Field(targetType = FieldType.OBJECT_ID)
    private String resolvedByDisputeId;

    private Instant releasedAt;

    public double getRemainingReleasableAmount() {
        return Math.max(0.0, heldAmount - frozenDisputedAmount - deductedForLateFee - deductedForDamage - refundedToCustomerAmount);
    }
}

