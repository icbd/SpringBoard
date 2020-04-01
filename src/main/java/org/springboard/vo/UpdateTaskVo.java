package org.springboard.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class UpdateTaskVo {

    @ApiModelProperty("移动Task到Listing, 此时parentUuid应该为空")
    private UUID listingUuid;

    @ApiModelProperty("移动子Task到父Task, 此时listingUuid应该为空")
    private UUID parentUuid;

    private String title;

    private String description;

    @ApiModelProperty("完成项目置为true")
    private Boolean completed;
}
