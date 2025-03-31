package com.exe.spring_cloud.msvc.oauth_service.services;

import com.exe.spring_cloud.msvc.libs_common_service.models.User;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final WebClient.Builder webClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {// Método que carga los detalles del usuario por nombre de usuario

        Map<String,String> params = new HashMap<>();
        params.put("username",username); // Se crea un map de parámetros con el nombre de usuario

        try {
            User user = webClient.build()
                    .get()
                    .uri("/username/{username}",params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
            // Se construye y ejecuta una solicitud WebClient para obtener el usuario por nombre de usuario

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role ->  new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            // Se mapean los roles del usuario a una lista de GrantedAuthority

            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getEnabled(),
                    true,true,true,roles);
            // Se retorna un objeto UserDetails con la información del usuario y sus roles

        }catch (WebClientResponseException e){
            throw new UsernameNotFoundException("Error en el login, no existe el usuario '" + username+ "'"+ " en el sistema");
            // Se lanza una excepción si no se encuentra el usuario
        }

    }
}
