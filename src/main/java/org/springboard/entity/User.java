package org.springboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NotBlank
    private String uuid;

    private Boolean enabled = false;

    @Email
    private String email;

    @NotBlank
    private String name;

    private String passwordDigest;

    @Transient
    private String password;

    private LocalDateTime deletedAt;

}
