package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UpdateRoleVo {

    private List<Long> permissionIds;

    private String title;

    private String description;
}
