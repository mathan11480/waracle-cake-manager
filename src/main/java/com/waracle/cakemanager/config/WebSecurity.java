package com.waracle.cakemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(a -> a
                .antMatchers(  "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login();

        http.cors();

        // this is disabled to get it working on the localhost
        http.csrf().disable();
    }
}
