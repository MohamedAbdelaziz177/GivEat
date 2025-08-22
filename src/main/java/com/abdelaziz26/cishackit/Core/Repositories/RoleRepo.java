package com.abdelaziz26.cishackit.Core.Repositories;

import com.abdelaziz26.cishackit.Core.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
