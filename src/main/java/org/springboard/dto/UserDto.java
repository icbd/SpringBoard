package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    
    private UUID uuid;
    
    private Boolean enabled;
    
    private String email;
    
    private String name;
    
    private ZonedDateTime createdAt;
    
    private ZonedDateTime updatedAt;
}
