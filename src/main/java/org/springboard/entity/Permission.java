package org.springboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springboard.constant.PermissionEnum;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    @NotNull
    private String sourceType;

    @NotNull
    private Long sourceId;

    @Convert
    private PermissionEnum code;
}
