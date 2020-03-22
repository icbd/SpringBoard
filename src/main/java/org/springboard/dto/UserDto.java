package org.springboard.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {
    
    private String uuid;
    
    private Boolean enabled;
    
    private String email;
    
    private String name;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
