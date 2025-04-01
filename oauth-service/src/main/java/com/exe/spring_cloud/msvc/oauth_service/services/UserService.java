package com.exe.spring_cloud.msvc.oauth_service.services;

import com.exe.spring_cloud.msvc.oauth_service.models.User;
import lombok.RequiredArgsConstructor;
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

    private final WebClient.Builder webClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = webClient.build()
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

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), user.getEnabled(),
                    true, true, true, roles);
        } catch (WebClientResponseException e) {
            throw new UsernameNotFoundException("Error en el login: " + e.getMessage());
        }
    }
}
