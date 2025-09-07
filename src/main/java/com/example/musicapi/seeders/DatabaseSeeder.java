package com.example.musicapi.seeders;

import com.example.musicapi.entities.Role;
import com.example.musicapi.entities.User;
import com.example.musicapi.enums.UserRoles;
import com.example.musicapi.repositories.IRoleRepository;
import com.example.musicapi.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void seed() {
        seedRolesTable();
        seedUsersTable();
    }

    private void seedRolesTable() {
        seedRole(UserRoles.USER);
        seedRole(UserRoles.ADMIN);
        seedRole(UserRoles.SYSADMIN);
    }

    private void seedUsersTable() {
        seedUser("ttrrbb81@gmail.com", "TrueBass", "ttrrbb81", UserRoles.SYSADMIN, 100000);
        seedUser("markverish@gmail.com", "markver", "markver2004", UserRoles.ADMIN, 100000);
        seedUser("IQLikeABread@gmail.com", "IQLikeABread", "IQLikeABread25", UserRoles.ADMIN, 100000);
    }

    private void seedUser(String email, String username, String rawPassword, UserRoles role, int socialCredit) {
        if (userRepository.findByUsername(username).isPresent() ||
            userRepository.findByEmail(email).isPresent() ||
            socialCredit < 0) {
            System.out.println("Skipped seeding user: " + username);
            return;
        }

        var user = User.builder().username(username)
                .email(email).password(passwordEncoder.encode(rawPassword))
                .socialCredit(socialCredit).roles(new HashSet<>()).build();

        var roleName = role.name();
        var specifiedRole = roleRepository.findByName(roleName);
        var defaultRole = roleRepository.findByName(UserRoles.USER.name());

        if (specifiedRole.isEmpty() || defaultRole.isEmpty()) {
            return;
        }
        if (role != UserRoles.USER) {
            user.getRoles().add(defaultRole.get());
        }

        user.getRoles().add(specifiedRole.get());
        userRepository.save(user);
    }

    private void seedRole(UserRoles role) {
        var roleName = role.name();
        if (roleRepository.findByName(roleName).isPresent())
            return;

        Role userRole = new Role();
        userRole.setName(roleName);
        roleRepository.save(userRole);
    }
}
