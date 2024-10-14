package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoadUsers {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public LoadUsers(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));
        Set<Role> rolesUser = new HashSet<>();
        Set<Role> rolesAdmin = new HashSet<>();
        rolesUser.add(roleRepository.findByName("ROLE_USER"));
        rolesAdmin.add(roleRepository.findByName("ROLE_ADMIN"));
        User user = new User("user", "user", "453422", 67,
                new BCryptPasswordEncoder().encode("100"), rolesUser);
        User admin = new User("admin", "admin", "453422", 67,
                new BCryptPasswordEncoder().encode("100"), rolesAdmin);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
