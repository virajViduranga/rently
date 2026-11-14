package com.rently.rently_backend.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
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
@Document(collection = "payouts")
public class Payout {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    private List<String> bookingIds;
    private String payoutType;
    @Builder.Default
    private String currency = "LKR";
    private Double grossAmount;
    private Double platformCommission;
    private Double netPayoutAmount;
    private PayoutDestinationSnapshot payoutDestinationSnapshot;

    @Indexed
    @Builder.Default
    private String status = "QUEUED";

    private String holdReason;
    private Instant paidAt;

    @CreatedDate
    private Instant createdAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayoutDestinationSnapshot {
        private String bankName;
        private String accountNumberMasked;
        private String pspTransferId;
    }
}

