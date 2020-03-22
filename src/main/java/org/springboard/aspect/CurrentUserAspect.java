package org.springboard.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springboard.entity.User;
import org.springboard.exception.PermissionErrorException;
import org.springboard.service.AccessTokenService;
import org.springboard.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.springboard.util.ControllerHelper.parseAccessTokenFrom;

/**
 * 解析 AccessToken, Set currentUser
 */
@Aspect
@Component
@RequiredArgsConstructor
public class CurrentUserAspect {

    private final UserService userService;
    private final AccessTokenService accessTokenService;

    @Pointcut("execution(public * org.springboard.controller.api.v1.*.*(..)) && " +
            "!execution(public * org.springboard.controller.api.v1.AccessTokenController.*(..)) &&" +
            "!execution(public * org.springboard.controller.api.v1.UserController.create(..))"
    )
    public void allControllerMethodsExceptAccessToken() {
    }

    @Before("allControllerMethodsExceptAccessToken()")
    public void doBeforeIndex(JoinPoint joinPoint) {
        try {
            User user = fetchCurrentUserFromToken();
            Object target = joinPoint.getTarget();
            Method setCurrentUserMethod = target.getClass().getMethod("setCurrentUser", User.class);
            setCurrentUserMethod.invoke(target, user);

        } catch (Exception e) {
            throw new RuntimeException("Set current user error.");
        }
    }

    private User fetchCurrentUserFromToken() {
        String bearer = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        Optional<String> token = parseAccessTokenFrom(bearer);
        token.orElseThrow(PermissionErrorException::new);
        return accessTokenService.getAccessToken(token.get()).getUser();
    }
}
