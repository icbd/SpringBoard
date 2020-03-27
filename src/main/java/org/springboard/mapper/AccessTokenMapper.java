package org.springboard.mapper;

import org.mapstruct.Mapper;
import org.springboard.dto.AccessTokenDto;
import org.springboard.entity.AccessToken;

@Mapper(componentModel = "spring")
public interface AccessTokenMapper {

    AccessTokenDto toAccessTokenDto(AccessToken accessToken);
}
