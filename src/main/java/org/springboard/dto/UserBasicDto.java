package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * User Basic Info
 */
@Getter
@Setter
public class UserBasicDto {

    private UUID uuid;

    private String name;
}
