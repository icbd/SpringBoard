package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccessTokenDto {

    private String token;

    private LocalDateTime expiredAt;

    private LocalDateTime createdAt;
}
