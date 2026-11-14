package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.DisputeStatus;
import lombok.*;
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
@EqualsAndHashCode(callSuper = true)
@Document(collection = "disputes")
public class Dispute extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String disputeNumber;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String bookingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String depositId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String raisedByUserId;

    private String raisedByRole;

    @Field(targetType = FieldType.OBJECT_ID)
    private String counterpartyUserId;

    private String disputeCategory;
    private Double disputedAmount;
    private String description;

    private EvidenceBundle evidenceBundle;
    private LegalIncidentPack legalIncidentPack;
    private DisputeResolution resolution;

    @Indexed
    @Builder.Default
    private DisputeStatus status = DisputeStatus.OPEN_AWAITING_COUNTERPARTY;

    @Field(targetType = FieldType.OBJECT_ID)
    private String assignedModeratorId;

    public boolean isResolutionSplitValid(double totalPoolAvailable) {
        if (resolution == null) return false;
        double sum = (resolution.getCustomerRefundAmount() != null ? resolution.getCustomerRefundAmount() : 0.0)
                + (resolution.getProviderPayoutAmount() != null ? resolution.getProviderPayoutAmount() : 0.0)
                + (resolution.getPlatformFeeRetainedAmount() != null ? resolution.getPlatformFeeRetainedAmount() : 0.0);
        return Math.abs(totalPoolAvailable - sum) < 0.01;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EvidenceBundle {
        @Field(targetType = FieldType.OBJECT_ID)
        private String preHandoverInspectionId;
        @Field(targetType = FieldType.OBJECT_ID)
        private String postReturnInspectionId;
        @Field(targetType = FieldType.OBJECT_ID)
        private String conversationId;
        private List<String> repairQuoteOrReceiptUrls;
        private AdditionalProofRequest additionalProofRequested;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdditionalProofRequest {
        @Field(targetType = FieldType.OBJECT_ID)
        private String requestedFromUserId;
        private String promptMessage;
        private Instant deadlineAt;
        private List<String> submittedUrls;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LegalIncidentPack {
        private Boolean isGenerated;
        private Instant generatedAt;
        private String exportPdfUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DisputeResolution {
        private String outcome; // REFUND_FULL, PARTIAL_REFUND_SPLIT, DEPOSIT_RELEASE, AGREED_DEDUCTION, NO_ADJUSTMENT (FR-SUP-04)
        private Double customerRefundAmount;
        private Double providerPayoutAmount;
        private Double platformFeeRetainedAmount;
        @Field(targetType = FieldType.OBJECT_ID)
        private String strikeIssuedToUserId;
        @Field(targetType = FieldType.OBJECT_ID)
        private String resolvedByAdminId;
        private String justificationSummary;
        private Instant resolvedAt;
    }
}

