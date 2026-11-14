package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "bookings")
public class Booking extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String bookingNumber;

    private ListingType bookingType;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String customerId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String listingId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String quoteId;

    @Builder.Default
    private Integer quantity = 1;

    private ListingSnapshot listingSnapshot;
    private PricingSnapshot pricingSnapshot;
    private BookingSchedule schedule;
    private FulfillmentDetails fulfillment;
    private ServiceTaskScope serviceTaskScope;

    @Indexed
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    private CancellationMeta cancellationMeta;

    @Field(targetType = FieldType.OBJECT_ID)
    private String reassignedFromBookingId;

    public boolean canCustomerRejectAtHandover() {
        return status == BookingStatus.CONFIRMED || status == BookingStatus.READY_FOR_PICKUP;
    }

    public boolean isInspectionWindowExpired() {
        return status == BookingStatus.RETURN_PENDING
                && schedule != null
                && schedule.getInspectionWindowExpiresAt() != null
                && Instant.now().isBefore(schedule.getInspectionWindowExpiresAt());
    }

    public double calculateOverduePenaltyNow() {
        if (schedule == null || schedule.getRequestedEndAt() == null || listingSnapshot == null) {
            return 0.0;
        }
        Instant end = schedule.getRequestedEndAt();
        if (Instant.now().isBefore(end)) {
            return 0.0;
        }
        long overdueHours = Math.max(1, Duration.between(end, Instant.now()).toHours());
        double rate = listingSnapshot.getPricingAtBooking().getLateFeePerHour() != null
                ? listingSnapshot.getPricingAtBooking().getLateFeePerHour()
                : 0.0;
        return overdueHours * rate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListingSnapshot {
        private String title;
        private ListingType listingType;
        private String primaryImageUrl;
        private List<String> baselinePhotosAtBooking;
        private List<String> includedAccessoriesAtBooking;
        private Double replacementValueAtBooking;
        private PricingAtBooking pricingAtBooking;
        private CancellationPolicy cancellationPolicyAtBooking;
        private Instant capturedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PricingAtBooking {
        private String currency;
        private Double hourlyRate;
        private Double dailyRate;
        private Double weeklyRate;
        private Double baseServiceFee;
        private Double securityDepositAmount;
        private Double lateFeePerHour;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PricingSnapshot {
        @Builder.Default
        private String currency = "LKR";
        @Builder.Default
        private Double subtotalAmount = 0.0;
        @Builder.Default
        private Double deliveryOrTravelFee = 0.0;
        @Builder.Default
        private Double approvedExtraMaterialsFee = 0.0;
        @Builder.Default
        private Double extensionFeeTotal = 0.0;
        @Builder.Default
        private Double latePenaltyTotal = 0.0;
        @Builder.Default
        private Double discountAmount = 0.0;
        @Builder.Default
        private Double securityDepositAmount = 0.0;
        @Builder.Default
        private Double platformFeeAmount = 0.0;
        @Builder.Default
        private Double totalChargeAmount = 0.0;
        @Builder.Default
        private Double netProviderPayoutEstimate = 0.0;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingSchedule {
        private Instant requestedStartAt;
        private Instant requestedEndAt;
        private Instant providerResponseDeadlineAt;
        private Instant paymentExpiryDeadlineAt;
        private Instant actualHandoverStartedAt;
        private Instant actualReturnedOrCompletedAt;
        private Instant inspectionWindowExpiresAt;
        private Instant pausedAt;
        @Builder.Default
        private Integer totalPausedMinutes = 0;
        @Builder.Default
        private List<ExtensionItem> extensionHistory = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtensionItem {
        private Instant previousEndAt;
        private Instant newEndAt;
        private Double additionalCharge;
        private Instant approvedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentDetails {
        private FulfillmentMethod method;
        private String handoverAddress;
        private GeoJsonPoint handoverGeo;
        private HandoverVerification handoverVerification;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HandoverVerification {
        private String startQrTokenHash;
        private String startPinHash;
        private Instant startVerifiedAt;
        private String returnQrTokenHash;
        private String returnPinHash;
        private Instant returnVerifiedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceTaskScope {
        private String problemDescription;
        @Builder.Default
        private List<String> customerTaskMediaUrls = new ArrayList<>();
        private String lockedEstimateNotes;
        @Builder.Default
        private List<ExtraMaterialReceipt> extraMaterialReceipts = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtraMaterialReceipt {
        private String itemName;
        private Double amount;
        private String receiptImageUrl;
        private Boolean customerApproved;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CancellationMeta {
        private String cancelledByRole;
        private String reasonCode;
        private String notes;
        private Instant cancelledAt;
    }
}

