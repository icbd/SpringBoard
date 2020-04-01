package org.springboard.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CreateListingVo {

    @NotBlank
    private UUID productUuid;

    @NotBlank
    private String title;

    private String description = "";
}
