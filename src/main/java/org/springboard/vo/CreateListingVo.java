package org.springboard.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateListingVo {

    @NotBlank
    private String productUuid;

    @NotBlank
    private String title;

    private String description = "";
}
