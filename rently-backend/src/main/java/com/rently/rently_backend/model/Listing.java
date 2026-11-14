package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.CancellationPolicy;
import com.rently.rently_backend.model.enums.ListingType;
import com.rently.rently_backend.model.enums.PricingModel;
import com.rently.rently_backend.model.enums.PublicationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "listings")
public class Listing extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed
    private ListingType listingType; // ITEM or SERVICE

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String providerUserId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String branchId;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String categoryId;

    @TextIndexed(weight = 3)
    private String title;

    @Indexed
    private String slug;

    @TextIndexed(weight = 1)
    private String description;

    @Builder.Default
    private List<ListingImage> images = new ArrayList<>();

    private ListingLocation location;

    private Pricing pricing;

    @Builder.Default
    private Boolean instantBookingEnabled = false; // FR-BKG-02

    @Builder.Default
    private CancellationPolicy cancellationPolicy = CancellationPolicy.FLEXIBLE_2H;

    @Builder.Default
    private Map<String, Object> customAttributes = new HashMap<>(); // FR-ITEM-02 / FR-DIS-02

    private ItemDetails itemDetails;
    private ServiceDetails serviceDetails;

    @Builder.Default
    private ReviewSummary reviewSummary = new ReviewSummary(0.0, 0);

    @Builder.Default
    private ModerationMeta moderationMeta = new ModerationMeta();

    @Indexed
    @Builder.Default
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    public void validatePolymorphicIntegrity() {
        if (listingType == ListingType.ITEM) {
            if (itemDetails == null) {
                throw new IllegalStateException("itemDetails must be populated when listingType is ITEM.");
            }
            this.serviceDetails = null;
        } else if (listingType == ListingType.SERVICE) {
            if (serviceDetails == null) {
                throw new IllegalStateException("serviceDetails must be populated when listingType is SERVICE.");
            }
            this.itemDetails = null;
        }
    }

    public Booking.ListingSnapshot toBookingSnapshot() {
        String primaryImg = images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                .map(ListingImage::getUrl)
                .findFirst()
                .orElse(images.isEmpty() ? null : images.get(0).getUrl());

        List<String> baselinePhotos = images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsBaselineConditionPhoto()))
                .map(ListingImage::getUrl)
                .collect(Collectors.toList());

        return Booking.ListingSnapshot.builder()
                .title(this.title)
                .listingType(this.listingType)
                .primaryImageUrl(primaryImg)
                .baselinePhotosAtBooking(baselinePhotos)
                .includedAccessoriesAtBooking(itemDetails != null ? itemDetails.getIncludedAccessories() : List.of())
                .replacementValueAtBooking(itemDetails != null ? itemDetails.getReplacementValue() : null)
                .pricingAtBooking(Booking.PricingAtBooking.builder()
                        .currency(pricing.getCurrency())
                        .hourlyRate(pricing.getHourlyRate())
                        .dailyRate(pricing.getDailyRate())
                        .weeklyRate(pricing.getWeeklyRate())
                        .baseServiceFee(pricing.getBaseServiceFee())
                        .securityDepositAmount(pricing.getSecurityDepositAmount() != null ? pricing.getSecurityDepositAmount() : 0.0)
                        .lateFeePerHour(pricing.getLateFeePerHour() != null ? pricing.getLateFeePerHour() : 0.0)
                        .build())
                .cancellationPolicyAtBooking(this.cancellationPolicy)
                .capturedAt(Instant.now())
                .build();
    }

    public void safePauseOrArchive(boolean hasActiveBookings) {
        if (hasActiveBookings) {
            this.publicationStatus = PublicationStatus.PAUSED_BY_PROVIDER;
        } else {
            this.publicationStatus = PublicationStatus.ARCHIVED;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListingImage {
        private String url;
        private String caption;
        @Builder.Default
        private Boolean isBaselineConditionPhoto = false;
        @Builder.Default
        private Boolean isPrimary = false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListingLocation {
        private String addressLine;
        private String publicAreaLabel;
        private String city;
        private String district;
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private GeoJsonPoint geo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pricing {
        @Builder.Default
        private String currency = "LKR";
        private PricingModel pricingModel;
        private Double hourlyRate;
        private Double dailyRate;
        private Double weeklyRate;
        private Double baseServiceFee;
        private Double perUnitRate;
        private String unitLabel;
        private Integer minRentalDurationHours; // FR-ITEM-03
        private Integer maxRentalDurationHours; // FR-ITEM-03
        @Builder.Default
        private Double securityDepositAmount = 0.0;
        private String depositClaimConditions; // FR-ITEM-04
        @Builder.Default
        private Double lateFeePerHour = 0.0;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDetails {
        private String brand;
        private String modelNumber;
        private String serialNumberMasked;
        private String itemCondition; // LIKE_NEW, GOOD, FAIR_TESTED
        private Double replacementValue;
        @Builder.Default
        private Integer totalStockQuantity = 1;
        @Builder.Default
        private Integer availableStockQuantity = 1;
        @Builder.Default
        private Integer turnaroundBufferMinutes = 60;
        @Builder.Default
        private List<String> includedAccessories = new ArrayList<>();
        private String usageSafetyInstructions;
        private String baselineConditionNotes;
        private FulfillmentOptions fulfillmentOptions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FulfillmentOptions {
        @Builder.Default
        private Boolean selfPickupAllowed = true;
        @Builder.Default
        private Boolean homeDeliveryAvailable = false;
        @Builder.Default
        private Boolean customerArrangedDeliveryAllowed = true; // FR-ITEM-08
        private Double maxDeliveryRadiusKm;
        private Double deliveryFeeBase;
        private Double deliveryFeePerKm;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceDetails {
        private String serviceMode; // ON_SITE_CUSTOMER_LOCATION, IN_SHOP_PROVIDER_LOCATION, REMOTE_ONLINE, HYBRID (FR-SVC-02)
        private Double serviceRadiusKm; // FR-SVC-03
        private String scopeSummary;    // FR-SVC-01
        private Integer estimatedDurationMinutes;
        @Builder.Default
        private Integer bufferMinutesAfterService = 30; // FR-SVC-05
        @Builder.Default
        private Boolean requiresCustomerTaskPhotos = true;
        @Builder.Default
        private Boolean requiresSiteInspectionFirst = false;
        @Builder.Default
        private Double siteInspectionFee = 0.0;
        @Builder.Default
        private Boolean bringsOwnToolsAndEquipment = true;
        private String materialsBillingPolicy;
        @Builder.Default
        private List<String> tradeCertifications = new ArrayList<>(); // FR-SVC-09
        @Builder.Default
        private List<String> workSampleUrls = new ArrayList<>();      // FR-SVC-09
        @Builder.Default
        private Integer warrantyDaysOnWork = 0;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewSummary {
        @Builder.Default
        private Double averageRating = 0.0;
        @Builder.Default
        private Integer totalReviews = 0;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModerationMeta {
        @Builder.Default
        private Double riskScore = 0.0;
        @Builder.Default
        private List<String> flaggedKeywords = new ArrayList<>();
        @Field(targetType = FieldType.OBJECT_ID)
        private String reviewedByModeratorId;
        private String rejectionReason;
    }
}

