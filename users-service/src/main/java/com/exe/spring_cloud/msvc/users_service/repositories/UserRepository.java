package com.exe.spring_cloud.msvc.users_service.repositories;

import com.exe.spring_cloud.msvc.libs_common_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
