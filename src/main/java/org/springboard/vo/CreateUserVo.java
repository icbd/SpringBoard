package org.springboard.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserVo {

    @Email
    private String email;

    @NotBlank
    private String name;

    @Size(min = 6)
    private String password;
}
