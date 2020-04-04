package org.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class AccessTokenDto {

    private String token;

    private ZonedDateTime expiredAt;

    private ZonedDateTime createdAt;
}
