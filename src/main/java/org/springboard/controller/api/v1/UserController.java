package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.UserDto;
import org.springboard.entity.User;
import org.springboard.mapper.UserMapper;
import org.springboard.service.UserService;
import org.springboard.vo.UpdateUserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "用户个人中心")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/user")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "查看个人信息")
    @GetMapping
    public ResponseEntity<UserDto> show() {
        UserDto userDto = userMapper.toUserDto(getCurrentUser());
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation("修改个人信息")
    @PatchMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UpdateUserVo vo) {
        User currentUser = getCurrentUser();
        userService.updateUser(currentUser, vo);
        UserDto userDto = userMapper.toUserDto(currentUser);
        return ResponseEntity.ok(userDto);
    }
}