package org.springboard.configuration;

import org.springboard.security.CurrentUserTokenFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springboard.controller.api.v1.BaseController.PREFIX;

@Configuration
public class CurrentUserTokenFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CurrentUserTokenFilter> configCurrentUserTokenFilter() {
        FilterRegistrationBean<CurrentUserTokenFilter> bean
                = new FilterRegistrationBean<>();
        CurrentUserTokenFilter currentUserTokenFilter = new CurrentUserTokenFilter();
        currentUserTokenFilter.addExcludeUrlPatterns(
                PREFIX + "/access_tokens/**"
                , PREFIX + "/register/**"
        );

        bean.setFilter(currentUserTokenFilter);
        //TODO: Exception Handler
        bean.addUrlPatterns("/*");
        return bean;
    }
}
