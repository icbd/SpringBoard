package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.UserDto;
import org.springboard.entity.User;
import org.springboard.mapper.UserMapper;
import org.springboard.service.UserService;
import org.springboard.vo.CreateUserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户注册")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/register")
public class RegisterController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation("用户注册")
    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserVo vo) {
        User user = userService.createUser(vo);
        UserDto userDto = userMapper.toUserDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
