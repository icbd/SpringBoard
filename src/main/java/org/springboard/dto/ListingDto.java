package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class ListingDto {

    private UUID uuid;

    private UserBasicDto creator;

    private String title;

    private String description;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
