package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharityRepository extends JpaRepository<Charity, Long> {

    Optional<Charity> findByUser_Id(Long userId);
}
