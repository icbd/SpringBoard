package org.springboard.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateUserVo;
import org.springboard.dto.UserDto;
import org.springboard.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User createUser(CreateUserVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User mergeUser(@MappingTarget User user, UpdateUserVo vo);
}
