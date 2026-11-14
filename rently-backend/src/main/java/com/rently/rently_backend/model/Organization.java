package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "organizations")
public class Organization extends BaseAuditDocument {

    @Id
    private String id;

    @Indexed
    @Field(targetType = FieldType.OBJECT_ID)
    private String ownerUserId;

    private String name;

    @Indexed(unique = true)
    private String slug;

    private String businessRegistrationNumber;
    private String taxIdentificationNumber;
    private String logoUrl;
    private String bannerUrl;

    @Builder.Default
    private List<Branch> branches = new ArrayList<>();

    @Builder.Default
    private String status = "PENDING_APPROVAL";

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Branch {
        @Field(targetType = FieldType.OBJECT_ID)
        private String branchId;
        private String name;
        private String phone;
        private BranchLocation location;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BranchLocation {
        private String address;
        private String city;
        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private GeoJsonPoint geo;
    }
}

