package com.exe.spring_cloud.msvc.users_service.services.Impl;


import com.exe.spring_cloud.msvc.users_service.models.Role;
import com.exe.spring_cloud.msvc.users_service.models.User;
import com.exe.spring_cloud.msvc.users_service.repositories.RoleRepository;
import com.exe.spring_cloud.msvc.users_service.repositories.UserRepository;
import com.exe.spring_cloud.msvc.users_service.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public User create(@RequestBody User user) {

        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(getRoles(user))
                .build();
        return userRepository.save(newUser);

    }


    @Transactional
    public Optional<User> update(Long id, User userDetails) {
        return Optional.ofNullable(userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEnabled(userDetails.getEnabled());
            user.setEmail(userDetails.getEmail());
            user.setRoles(getRoles(user));
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private List<Role> getRoles(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        roleOptional.ifPresent(roles::add);

        if (user.isAdmin()) {
            roleOptional = roleRepository.findByName("ROLE_ADMIN");
            roleOptional.ifPresent(roles::add);
        }

        return roles;
    }
}
