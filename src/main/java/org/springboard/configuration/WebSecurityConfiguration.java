package org.springboard.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Configuration
    @Order(1)
    class SwaggerBasicAuthConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private static final String SYS_DOC_ROLE = "SYS_DOC";

        @Value("${doc.swagger.user}")
        private String docUser;
        @Value("${doc.swagger.password}")
        private String docPassword;

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/swagger-ui.html").hasRole(SYS_DOC_ROLE)
                    .and().httpBasic()
            ;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                .withUser(docUser)
                .password(passwordEncoder.encode(docPassword))
                .roles(SYS_DOC_ROLE);
        }
    }

    @Configuration
    @Order(2)
    class TokenSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        //TODO: Fill
        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .anyRequest().permitAll()
            ;
        }
    }
}
