package org.springboard.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateUserVo {

    @Email
    private String email;

    @NotBlank
    private String name;
}
