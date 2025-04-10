package com.exe.spring_cloud.msvc.oauth_service.services;

import com.exe.spring_cloud.msvc.oauth_service.models.User;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final WebClient webClient;

    private final Tracer tracer;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Llamada a metodo del service UserService:loadUserByUsername, login con: {},", username);

        try {
            User user = webClient
                    .get()
                    .uri("/username/{username}", username)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            if (user == null) {
                throw new UsernameNotFoundException("Usuario '" + username + "' no encontrado");
            }

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            logger.info("Se ha realizado el login con exito by username: {}", user);
            tracer.currentSpan().tag("success.login", "Se ha realizado el login con exito by username: " + user);

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.getEnabled(),
                    true, true, true, roles);
        } catch (WebClientResponseException e) {
            String error = "Error en el login, no existe el users '" + username + "' en el servicio de usuarios";
            logger.error(error);
            tracer.currentSpan().tag("error.login", error);
            throw new UsernameNotFoundException(error);
        }
    }
}
