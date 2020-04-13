package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
public class CreateRoleVo {

    @NotNull
    private List<Long> permissionIds;

    @NotNull
    private String title;

    private String description;
}
