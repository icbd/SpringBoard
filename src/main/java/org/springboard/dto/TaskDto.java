package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class TaskDto {

    private UUID uuid;

    private UUID listingUuid;

    private UserBasicDto creator;

    private UUID parentTaskUuid;

    private String title;

    private String description;

    private ZonedDateTime completedAt;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
