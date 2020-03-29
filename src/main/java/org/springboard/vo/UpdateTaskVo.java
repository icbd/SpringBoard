package org.springboard.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskVo {

    @ApiModelProperty("移动Task到Listing, 此时parentUuid应该为空")
    private String listingUuid;

    @ApiModelProperty("移动子Task到父Task, 此时listingUuid应该为空")
    private String parentUuid;

    private String title;

    private String description;

    @ApiModelProperty("完成项目置为true")
    private Boolean completed = false;
}
