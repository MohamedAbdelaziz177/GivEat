package com.abdelaziz26.cishackit.Core.Repositories;

import com.abdelaziz26.cishackit.Core.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
