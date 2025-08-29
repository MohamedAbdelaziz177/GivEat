package com.abdelaziz26.GivEat.Utils;

import com.abdelaziz26.GivEat.Core.Entities.Role;
import com.abdelaziz26.GivEat.Core.Enums.RoleEn;
import com.abdelaziz26.GivEat.Core.Repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepo roleRepository;

    @Override
    public void run(String... args) {
        seedRole("ROLE_ADMIN");
        seedRole("ROLE_RESTAURANT");
        seedRole("ROLE_CHARITY");
    }

    private void seedRole(String roleName) {

        Role role = new Role();
        role.setName(RoleEn.valueOf(roleName));

        roleRepository.findByName(RoleEn.valueOf(roleName))
                .orElseGet(() -> roleRepository.save(role));
    }
}
