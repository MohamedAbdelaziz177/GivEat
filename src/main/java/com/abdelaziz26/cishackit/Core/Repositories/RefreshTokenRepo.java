package com.abdelaziz26.cishackit.Core.Repositories;

import com.abdelaziz26.cishackit.Core.Entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    void deleteAllByUser_Id(Long userId);
}
