package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springboard.constant.PermissionEnum;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    @NotNull
    @Type(type = "uuid-char")
    private UUID uuid;

    @Builder.Default
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    @NotNull
    private String sourceType;

    @NotNull
    private Long sourceId;

    private PermissionEnum code;
}
