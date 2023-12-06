package com.example.booksstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    @Qualifier("OAuth2SuccessHandler")
    private AuthenticationSuccessHandler oauth2handler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.oauth2Login(oauth -> oauth.loginPage("/login")
                .successHandler(oauth2handler))
                .formLogin(form -> form.loginPage("/login"));
        return http.build();
    }
}