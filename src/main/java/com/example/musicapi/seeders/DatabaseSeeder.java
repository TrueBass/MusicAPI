package com.example.musicapi.seeders;

import com.example.musicapi.entities.Role;
import com.example.musicapi.repositories.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final IRoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        seedRolesTable();
    }

    private void seedRolesTable() {
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
    }
}
