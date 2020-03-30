package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskDto {

    private String uuid;

    private String listingUuid;

    private UserBasicDto creator;

    private String parentTaskUuid;

    private String title;

    private String description;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
