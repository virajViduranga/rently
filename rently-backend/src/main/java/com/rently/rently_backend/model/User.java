package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class User extends BaseAuditDocument {

    @Id
    private String id;

    @Email
    @NotBlank
    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true, sparse = true)
    private String phone;

    private String passwordHash;

    private List<AuthProviderLink> authProviders;

    @NotEmpty
    @Builder.Default
    private Set<Role> roles = new HashSet<>(Set.of(Role.CUSTOMER));

    @Builder.Default
    private ActiveMode activeMode = ActiveMode.CUSTOMER;

    private UserProfile profile;

    @Builder.Default
    private TrustProfile trustProfile = new TrustProfile();

    private PayoutSettings payoutSettings;

    @Builder.Default
    private UserStatus status = UserStatus.PENDING_VERIFICATION;

    @Builder.Default
    private String providerStatus = "NONE"; // NONE, PENDING, ACTIVE, RESTRICTED, SUSPENDED (FR-PRO-06)

    private Instant providerTermsAcceptedAt; // FR-PRO-01

    private Instant deletedAt;

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }

    public boolean canRentHighValueItems() {
        return status == UserStatus.ACTIVE
                && trustProfile != null
                && (trustProfile.getKycTier() == KycTier.TIER_2_NIC_SELFIE
                    || trustProfile.getKycTier() == KycTier.TIER_3_PRO_CERTIFIED)
                && trustProfile.getStrikes() < 3;
    }

    public void addStrikeAndEvaluateSuspension() {
        if (this.trustProfile == null) {
            this.trustProfile = new TrustProfile();
        }
        this.trustProfile.setStrikes(this.trustProfile.getStrikes() + 1);
        if (this.trustProfile.getStrikes() >= 3) {
            this.status = UserStatus.SUSPENDED;
            this.providerStatus = "SUSPENDED";
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthProviderLink {
        private String provider;
        private String providerUserId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfile {
        private String firstName;
        private String lastName;
        private String displayName;
        private String avatarUrl;
        private String bio;
        @Builder.Default
        private String preferredLanguage = "en";
        private DefaultLocation defaultLocation;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultLocation {
        private String addressLine;
        private String city;
        private String district;
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private GeoJsonPoint coordinates;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrustProfile {
        @Builder.Default
        private KycTier kycTier = KycTier.TIER_0_UNVERIFIED;
        @Builder.Default
        private Boolean emailVerified = false;
        @Builder.Default
        private Boolean phoneVerified = false;
        @Builder.Default
        private Boolean identityVerified = false;
        @Builder.Default
        private Boolean tradeLicenseVerified = false;
        @Builder.Default
        private Double trustScore = 50.0;
        @Builder.Default
        private Integer completedBookingsCount = 0;
        @Builder.Default
        private Double onTimeReturnRate = 100.0;
        @Builder.Default
        private Double responseRate = 100.0;
        @Builder.Default
        private Integer strikes = 0;
        @Builder.Default
        private Boolean isHighValueEligible = false;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayoutSettings {
        private String bankName;
        private String branchCode;
        private String accountHolderName;
        private String accountNumberMasked;
        private String pspRecipientId;
        private String autoPayoutSchedule;
    }
}

