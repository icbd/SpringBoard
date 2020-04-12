package org.springboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @OneToOne
    @JsonIgnore
    private User creator;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<RoleAndPermission> roleAndPermissions = new ArrayList<>();

    @NotBlank
    private String title;

    private String description;
}
