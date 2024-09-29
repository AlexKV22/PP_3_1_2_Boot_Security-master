package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String findAllUsers(ModelMap model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "find-all-users";
    }

    @GetMapping(value = "/actionUserForm")
    public String addUserRedirect() {
        return "add-user";
    }

    @PostMapping(value ="/addUser")
    public String addUser(@ModelAttribute("user") @Validated User user) {
        userService.save(user);
        return "redirect:/";
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("deleteUser") Integer id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.delete(byId.get());
        }
        return "redirect:/";
    }

    @GetMapping(value = "/findUser")
    public String findUserById(@RequestParam("findID") Integer id, ModelMap model) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute("user", byId.get());
        }
        return "find-user-by-id";
    }

    @GetMapping(value = "/actionUpdateForm")
    public String updateUserRedirect(ModelMap model) {
        List<User> allUsers1 = userService.findAll();
        model.addAttribute("allUsers1", allUsers1);
        return "update-user";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user1") @Validated User user, @RequestParam("updateUser") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/";
    }







    @GetMapping(value = "/index")
    public String admin() {
        return "index";
    }

    @PostMapping(value = "/login")
    public String admin2() {
        return "update-user";
    }
}
