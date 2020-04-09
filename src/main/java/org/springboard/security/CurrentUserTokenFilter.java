package org.springboard.security;

import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.exception.PermissionErrorException;
import org.springboard.service.AccessTokenService;
import org.springframework.core.annotation.Order;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springboard.util.ControllerHelper.parseAccessTokenFrom;

@Order(1)
public class CurrentUserTokenFilter extends OncePerRequestFilter {

    private AccessTokenService accessTokenService;
    private Set<String> excludeUrlPatterns = new HashSet<>();
    protected AntPathMatcher antPathMatcher = new AntPathMatcher();

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
}
