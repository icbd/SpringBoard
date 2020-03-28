package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.AccessTokenDto;
import org.springboard.entity.AccessToken;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.mapper.AccessTokenMapper;
import org.springboard.service.AccessTokenService;
import org.springboard.service.LoginService;
import org.springboard.vo.PasswordLoginVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

import static org.springboard.util.ControllerHelper.parseAccessTokenFrom;

@Api(tags = "登录令牌")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/access_tokens")
public class AccessTokenController extends BaseController {

    private final AccessTokenService accessTokenService;
    private final LoginService loginService;
    private final AccessTokenMapper accessTokenMapper;

    @ApiOperation("查看令牌信息")
    @GetMapping
    public ResponseEntity<AccessTokenDto> show(@RequestHeader("Authorization") String bearer) {
        Optional<String> tokenOptional = parseAccessTokenFrom(bearer);
        tokenOptional.orElseThrow(AuthenticationErrorException::new);

        AccessToken accessToken = accessTokenService.getAccessToken(tokenOptional.get());
        AccessTokenDto accessTokenDto = accessTokenMapper.toAccessTokenDto(accessToken);
        return ResponseEntity.ok(accessTokenDto);
    }

    @ApiOperation("登录: 获取令牌")
    @PostMapping
    public ResponseEntity<AccessTokenDto> create(@Valid @RequestBody PasswordLoginVo vo) {
        AccessToken accessToken = loginService.loginByPassword(vo.getEmail(), vo.getPassword());
        AccessTokenDto accessTokenDto = accessTokenMapper.toAccessTokenDto(accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(accessTokenDto);
    }

    @ApiOperation("登出此设备")
    @DeleteMapping
    public ResponseEntity<Void> destroy(@RequestHeader("Authorization") String bearer) {
        Optional<String> tokenOptional = parseAccessTokenFrom(bearer);
        tokenOptional.orElseThrow(AuthenticationErrorException::new);

        AccessToken accessToken = accessTokenService.getAccessToken(tokenOptional.get());
        accessTokenService.destroyAccessToken(accessToken.getId());
        return ResponseEntity.noContent().build();
    }
}
