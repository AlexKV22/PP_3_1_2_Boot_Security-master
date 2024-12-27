package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    List<User> findAll();
    Optional<User> findById(Integer id);
    void delete(User user);
    void updateUser(User user);
    User findByUsername(String username);
}
