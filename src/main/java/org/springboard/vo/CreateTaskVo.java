package org.springboard.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
public class CreateTaskVo {

    // TODO listingUuid parentUuid 二选一 validation

    @ApiModelProperty("添加Task到Listing, 此时parentUuid应该为空")
    private String listingUuid;

    @ApiModelProperty("添加子Task到父Task, 此时listingUuid应该为空")
    private String parentUuid;

    @NotBlank
    private String title;

    private String description = "";
}
