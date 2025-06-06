package com.exe.spring_cloud.msvc.users_service.repositories;



import com.exe.spring_cloud.msvc.users_service.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
