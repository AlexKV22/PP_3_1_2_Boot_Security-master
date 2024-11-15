package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;



@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String findAllUsers(ModelMap model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("userAdmin", user);
        List<User> allUsers = userService.findAll();
        model.addAttribute("allUsers", allUsers);
        User newUser = new User();
        model.addAttribute("newUser", newUser);
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "find-all-users";
    }

    @GetMapping(value = "/actionUserForm")
    public String addUserRedirect(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", new User());
        Set<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("userAdmin", user);
        return "add-user";
    }

    @PostMapping(value ="/addUser")
    public String addUser(@ModelAttribute @Validated User user)
    {
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("userId") Integer id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.delete(byId.get());
        }
        return "redirect:/admin";
    }

    @GetMapping(value = "/findUser")
    public String findUserById(@ModelAttribute("adminID") Integer id, ModelMap model) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute("user", byId.get());
        }
        return "find-user-by-id";
    }


    @PostMapping(value = "/updateUser")
    public String updateUser(@ModelAttribute @Validated User user, @RequestParam("userId") Integer id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }









    //    @GetMapping(value = "/actionUpdateForm")
//    public String updateUserRedirect(ModelMap model, @RequestParam Integer id) {
//        Optional<User> user = userService.findById(id);
//        if (user.isPresent()) {
//            model.addAttribute("user", user.get());
//            Set<Role> roles = roleService.getAllRoles();
//            model.addAttribute("roles", roles);
//            User newUser = new User();
//            model.addAttribute("newUser", newUser);
//        }
//        return "update-user";
//    }






    //    @GetMapping(value = "/actionDeleteForm")
//    public String deleteUserForm(ModelMap model, @RequestParam Integer id) {
//        Optional<User> user = userService.findById(id);
//        if (user.isPresent()) {
//            model.addAttribute("user", user.get());
//        }
//        return "delete-user";
//    }
}
