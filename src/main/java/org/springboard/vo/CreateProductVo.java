package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
public class CreateProductVo {

    @NotBlank
    private String title;

    private String description = "";
}
