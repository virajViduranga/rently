package com.rently.rently_backend.model;

import com.rently.rently_backend.model.common.BaseAuditDocument;
import com.rently.rently_backend.model.enums.OrgPermission;
import com.rently.rently_backend.model.enums.OrgRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "organization_members")
@CompoundIndex(name = "org_user_unique_idx", def = "{'organizationId': 1, 'userId': 1}", unique = true)
public class OrganizationMember extends BaseAuditDocument {

    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String organizationId;

    @Field(targetType = FieldType.OBJECT_ID)
    private String userId;

    private List<String> assignedBranchIds;
    private OrgRole role;
    private Set<OrgPermission> permissions;

    @Builder.Default
    private String status = "ACTIVE";

    @Field(targetType = FieldType.OBJECT_ID)
    private String invitedByUserId;

    public boolean hasPermission(OrgPermission permission) {
        return "ACTIVE".equals(status) && permissions != null && permissions.contains(permission);
    }
}

