package com.example.dbpfinal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v2/api-docs", "/v3/api-docs/**",
                        "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/member/**", "/bookmark/**", "/store/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();

        return http.build();
    }
}
