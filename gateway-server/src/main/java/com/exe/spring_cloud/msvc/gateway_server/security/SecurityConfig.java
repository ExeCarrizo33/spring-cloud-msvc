package com.exe.spring_cloud.msvc.gateway_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange(authz -> {
            authz.pathMatchers("/authorized","/logout").permitAll()
                    .pathMatchers(HttpMethod.GET,"/api/items","/api/products","/api/users").permitAll()
                    .pathMatchers(HttpMethod.GET, "/api/items/{id}","/api/products/{id}","/api/users/{id}").hasAnyRole("ADMIN","USER")
                    .pathMatchers("/api/items/**","/api/products/**","/api/users/**").hasRole("ADMIN")
                    .anyExchange().authenticated();
                }).cors(csrf -> csrf.disable())
                .oauth2Login(withDefaults())
                .oauth2Login(withDefaults())
                .oauth2ResourceServer(withDefaults())
                .build();

    }

}
