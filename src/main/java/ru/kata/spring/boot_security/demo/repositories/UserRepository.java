package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query("update User set username = ?1, age = ?2 where id = ?3 ")
    @Query("update User set username = ?1, lastName = ?2, age = ?3, email =?4, password = ?5 where id = ?6")
    @Modifying
    @Transactional
    void updateUser(String name, String lastName, Integer age, String email, String password, Integer id);

    User findByUsername(String username);

    Optional<User> findById(Integer id);


}
