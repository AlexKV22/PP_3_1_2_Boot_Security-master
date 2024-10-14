package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Optional;



public interface UserServiceInterface {
    void saveUser(User user, Integer roleId);
    List<User> findAll();
    Optional<User> findById(Integer id);
    void delete(User user);
    void updateUser(Integer id, User user, Integer idrole);
    User findByUsername(String username);
}
