package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceInterface;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServiceInterface userService;

    @Autowired
    private RoleServiceInterface roleService;

    public AdminController(UserServiceInterface userService, RoleServiceInterface roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "add-user";
    }

    @PostMapping(value ="/addUser")
    public String addUser(@ModelAttribute @Validated User user, @RequestParam("addRole") Integer roleId) {
        userService.saveUser(user, roleId);
        return "redirect:/admin";
    }

    @GetMapping(value = "/actionDeleteForm")
    public String deleteUserForm(ModelMap model, @RequestParam Integer id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }
        return "delete-user";
    }


    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("id") Integer id) {
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
    public String updateUserRedirect(ModelMap model, @RequestParam Integer id) {
        Optional<User> user = userService.findById(id);
        System.out.println(user);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            Set<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            User newUser = new User();
            model.addAttribute("newUser", newUser);
        }
        return "update-user";
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute @Validated User user, @RequestParam("id") Integer id, @RequestParam("updateRole") @Validated Integer idrole) {
        System.out.println(idrole);
        userService.updateUser(id, user, idrole);
        return "redirect:/admin";
    }
}
