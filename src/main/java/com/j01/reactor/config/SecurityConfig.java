package com.j01.reactor.config;

import com.j01.reactor.model.User;
import com.j01.reactor.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/api/tacos", "/orders").hasAuthority("ROLE_USER")
                .anyExchange().permitAll()
                .and()
                .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(
            UserRepo userRepo) {
        return new ReactiveUserDetailsService() {
            @Override
            public Mono<UserDetails> findByUsername(String username) {
                return userRepo.findByUsername(username)
                        .map(User::toUserDetails);
            }
        };
    }
}