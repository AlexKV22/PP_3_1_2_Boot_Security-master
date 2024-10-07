package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String findAllUsers(ModelMap model) {
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);
        return "find-all-users";
    }

    @GetMapping(value = "/actionUserForm")
    public String addUserRedirect(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping(value ="/addUser")
    public String addUser(@ModelAttribute @Validated User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("deleteUser") Integer id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.delete(byId.get());
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/findUser")
    public String findUserById(@ModelAttribute("findID") Integer id, ModelMap model) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute("user", byId.get());
        }
        return "find-user-by-id";
    }

    @GetMapping(value = "/actionUpdateForm")
    public String updateUserRedirect(ModelMap model) {
        List<User> allUsers1 = userService.findAll();
        User newUser = new User();
        model.addAttribute("allUsers1", allUsers1);
        model.addAttribute("newUser", newUser);
        return "update-user";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute("user") @Validated User user, @RequestParam("updateUser") Integer id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }
}
