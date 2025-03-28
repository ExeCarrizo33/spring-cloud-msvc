package com.exe.spring_cloud.msvc.gateway_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults; // Importa el método withDefaults de la clase Customizer

@Configuration
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange(authz -> { // Configura la autorización de intercambios
            authz.pathMatchers("/authorized","/logout").permitAll() // Permite el acceso a las rutas /authorized y /logout a todos
                    .pathMatchers(HttpMethod.GET,"/api/items","/api/products","/api/users").permitAll() // Permite el acceso GET a las rutas /api/items, /api/products y /api/users a todos
                    .pathMatchers(HttpMethod.GET, "/api/items/{id}","/api/products/{id}","/api/users/{id}").hasAnyRole("ADMIN","USER") // Permite el acceso GET a las rutas con cualquier subruta a usuarios con roles ADMIN o USER
                    .pathMatchers("/api/items/**","/api/products/**","/api/users/**").hasRole("ADMIN") // Permite el acceso a las rutas con cualquier subruta a usuarios con rol ADMIN
                    .anyExchange().authenticated(); // Requiere autenticación para cualquier otro intercambio
                }).cors(csrf -> csrf.disable()) // Deshabilita la protección CSRF
                .oauth2Login(withDefaults()) // Habilita el inicio de sesión OAuth2 con la configuración predeterminada
                .oauth2Client(withDefaults()) // Habilita el cliente OAuth2 con la configuración predeterminada
                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<? extends AbstractAuthenticationToken>>() { // Configura el convertidor de autenticación JWT
                            @Override
                            public Mono<? extends AbstractAuthenticationToken> convert(Jwt source) { // Convierte un token JWT en un token de autenticación
                                Collection<String> roles = source.getClaimAsStringList("roles"); // Obtiene los roles del token JWT
                                Collection<GrantedAuthority> authorities = roles.stream() // Convierte los roles en una secuencia de autoridades
                                        .map(SimpleGrantedAuthority::new) // Mapea los roles a una colección de autoridades
                                        .collect(Collectors.toList());
                                return Mono.just(new JwtAuthenticationToken(source,authorities)); // Devuelve un token de autenticación JWT
                            }
                        })
                )) // Configura el servidor de recursos OAuth2 para usar JWT
                .build(); // Construye y devuelve la cadena de filtros de seguridad

    }

}
