package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springboard.constant.PermissionEnum;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "permission")
    private List<RoleAndPermission> roleAndPermissions = new ArrayList<>();

    @NotNull
    private String sourceType;

    @NotNull
    private Long sourceId;

    private PermissionEnum code;
}
