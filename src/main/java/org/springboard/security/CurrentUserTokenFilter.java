package org.springboard.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springboard.dto.ErrorDto;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.exception.PermissionErrorException;
import org.springboard.handler.AuthenticationErrorExceptionHandler;
import org.springboard.handler.PermissionErrorExceptionHandler;
import org.springboard.service.AccessTokenService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springboard.configuration.JacksonTimeFormatConfiguration.ISO_DATETIME_FORMAT;
import static org.springboard.util.ControllerHelper.parseAccessTokenFrom;

@Order(1)
public class CurrentUserTokenFilter extends OncePerRequestFilter {

    private AccessTokenService accessTokenService;
    private Set<String> excludeUrlPatterns;
    private AntPathMatcher antPathMatcher;
    private AuthenticationErrorExceptionHandler authenticationErrorExceptionHandler;
    private PermissionErrorExceptionHandler permissionErrorExceptionHandler;
    private ObjectMapper objectMapper;

    public CurrentUserTokenFilter() {
        excludeUrlPatterns = new HashSet<>();
        antPathMatcher = new AntPathMatcher();
        authenticationErrorExceptionHandler = new AuthenticationErrorExceptionHandler();
        permissionErrorExceptionHandler = new PermissionErrorExceptionHandler();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat(ISO_DATETIME_FORMAT));
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        checkAccessTokenService(request);
        try {
            User user = fetchCurrentUserFromToken();
            CurrentUserContextHolder.setContext(user);
            if (!user.getEnabled()) {
                throw new PermissionErrorException("Account has not been activated.");
            }
            filterChain.doFilter(request, response);

        } catch (AuthenticationErrorException e) {
            ResponseEntity<ErrorDto> responseEntity
                    = authenticationErrorExceptionHandler.handlerAuthenticationErrorException(e);
            flushResponseEntity(response, responseEntity);
        } catch (PermissionErrorException e) {
            ResponseEntity<ErrorDto> responseEntity =
                    permissionErrorExceptionHandler.handlerPermissionErrorException(e);
            flushResponseEntity(response, responseEntity);
        } catch (Exception e) {
            throw e;
        } finally {
            CurrentUserContextHolder.clearContext();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludeUrlPatterns.stream().anyMatch(p -> antPathMatcher.match(p, request.getRequestURI()));
    }

    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    private User fetchCurrentUserFromToken() {
        String bearer = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        Optional<String> token = parseAccessTokenFrom(bearer);
        return token.map(t -> {
            AccessToken accessToken = accessTokenService.getAccessToken(t);
            return accessToken.getUser();
        }).orElseThrow(AuthenticationErrorException::new);
    }

    private void checkAccessTokenService(HttpServletRequest request) {
        if (accessTokenService == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
            accessTokenService = webApplicationContext.getBean(AccessTokenService.class);
        }
    }

    private void flushResponseEntity(HttpServletResponse response, ResponseEntity responseEntity) {
        response.setStatus(responseEntity.getStatusCodeValue());

        HttpHeaders headers = responseEntity.getHeaders();
        response.addHeader("Content-Type", "application/json");
        for (String headerKey : headers.keySet()) {
            response.addHeader(headerKey, String.valueOf(headers.get(headerKey)));
        }

        try {
            String httpBodyContent = objectMapper.writeValueAsString(responseEntity.getBody());
            response.getOutputStream().write(httpBodyContent.getBytes());
        } catch (IOException e) {
        }
    }
}
