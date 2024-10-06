package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String findAllUsers(ModelMap model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "find-all-users";
    }

    @GetMapping(value = "/admin/actionUserForm")
    public String addUserRedirect() {
        return "add-user";
    }

    @PostMapping(value ="/admin/addUser")
    public String addUser(@ModelAttribute("user") @Validated User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam("deleteUser") Integer id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.delete(byId.get());
        }
        return "redirect:/";
    }

    @GetMapping(value = "/admin/findUser")
    public String findUserById(@ModelAttribute("findID") Integer id, ModelMap model) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute("user", byId.get());
        }
        return "find-user-by-id";
    }

    @GetMapping(value = "/admin/actionUpdateForm")
    public String updateUserRedirect(ModelMap model) {
        List<User> allUsers1 = userService.findAll();
        model.addAttribute("allUsers1", allUsers1);
        return "update-user";
    }

    @PostMapping(value = "/admin/updateUser")
    public String updateUser(@ModelAttribute("user1") @Validated User user, @RequestParam("updateUser") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/";
    }
}
