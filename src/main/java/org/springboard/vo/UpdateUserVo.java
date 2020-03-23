package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
public class UpdateUserVo {

    @Email
    private String email;

    @NotBlank
    private String name;
}
