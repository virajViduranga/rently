package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.KycTier;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "categories")
public class Category extends BaseAuditDocument {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String parentCategoryId;

    private String name;

    @Indexed(unique = true)
    private String slug;

    private String categoryType; // ITEM, SERVICE, BOTH
    private String iconUrl;
    private PolicyRules policyRules;
    private List<AttributeFieldSchema> attributeSchema;

    @Builder.Default
    private Boolean isActive = true;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PolicyRules {
        private KycTier minKycTierForProvider;
        private KycTier minKycTierForCustomer;
        private Boolean requiresMandatoryConditionPhotos;
        private Boolean requiresFunctionalVideoTest;
        private Double recommendedDepositPercentage;
        private Double platformCommissionPercent;
        private Integer defaultTurnaroundBufferMinutes;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttributeFieldSchema {
        private String key;
        private String label;
        private String inputType; // NUMBER, SELECT, BOOLEAN, TEXT
        private String unit;
        private Boolean required;
    }
}

