package org.springboard.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springboard.dto.RoleDto;
import org.springboard.entity.Role;
import org.springboard.vo.CreateRoleVo;
import org.springboard.vo.UpdateRoleVo;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);

    Role createRole(CreateRoleVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Role mergeRole(@MappingTarget Role role, UpdateRoleVo vo);
}
