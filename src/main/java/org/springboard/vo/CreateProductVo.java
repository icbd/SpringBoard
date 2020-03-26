package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateProductVo {

    private String title;

    private String description;
}
