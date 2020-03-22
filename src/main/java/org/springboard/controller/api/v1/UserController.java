package org.springboard.controller.api.v1;

import lombok.RequiredArgsConstructor;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateUserVo;
import org.springboard.dto.UserDto;
import org.springboard.entity.User;
import org.springboard.mapper.UserMapper;
import org.springboard.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/users")
public class UserController extends BaseController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> show(@PathVariable String uuid) {
        User user = userService.getUserByUuid(uuid);
        assertOnlyFor(user);

        UserDto userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserVo vo) {
        User user = userService.createUser(vo);
        UserDto userDto = userMapper.toUserDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<UserDto> update(@PathVariable String uuid, @RequestBody UpdateUserVo vo) {
        User user = userService.getUserByUuid(uuid);
        assertOnlyFor(user);

        userService.updateUser(user, vo);
        UserDto userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> destroy(@PathVariable String uuid) {
        User user = userService.getUserByUuid(uuid);
        assertOnlyFor(user);

        userService.destroyUser(user.getId());
        return ResponseEntity.noContent().build();
    }
}