package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.VerificationStatus;
import com.rently.rently_backend.model.enums.VerificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "verification_records")
@CompoundIndex(name = "user_verif_type_status_idx", def = "{'userId': 1, 'verificationType': 1, 'status': 1}")
public class VerificationRecord extends BaseAuditDocument {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    private VerificationType verificationType;

    @Builder.Default
    private VerificationStatus status = VerificationStatus.PENDING;

    private String otpOrResetTokenHash; // Used for EMAIL_OTP, PHONE_OTP, and single-use PASSWORD_RESET_TOKEN (FR-ACC-03)
    private Instant tokenExpiresAt;

    private DocumentData documentData;

    @Builder.Default
    private List<FailedField> failedFields = new ArrayList<>();

    @Field(targetType = FieldType.OBJECT_ID)
    private String reviewedByAdminId;

    private Instant reviewedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DocumentData {
        private String documentNumberHash;
        private String documentNumberMasked;
        private String frontImageUrl;
        private String backImageUrl;
        private String selfieImageUrl;
        private Instant issuedDate;
        private Instant expiryDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailedField {
        private String field;
        private String reasonTag;
        private String message;
    }
}

