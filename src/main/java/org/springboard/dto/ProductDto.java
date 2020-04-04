package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {

    private UUID uuid;

    private UserBasicDto creator;

    private String title;

    private String description;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
