package com.exe.spring_cloud.msvc.users_service.services;


import com.exe.spring_cloud.msvc.users_service.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User create(User user);

    Optional<User> update(Long id, User user);

    void delete(Long id);


}
