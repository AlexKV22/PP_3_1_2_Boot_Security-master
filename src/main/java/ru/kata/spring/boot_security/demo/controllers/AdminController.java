package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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



@RestController
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
    public ResponseEntity<List<User>> findAllUsers() {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("userAdmin", user);
//        List<User> allUsers = userService.findAll();
//        model.addAttribute("allUsers", allUsers);
//        User newUser = new User();
//        model.addAttribute("newUser", newUser);
//        Set<Role> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles);
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
//        return "find-all-users";
    }

//    @GetMapping(value = "/actionUserForm")
//    public String addUserRedirect(Model model, Principal principal) {
//        User user = userService.findByUsername(principal.getName());
//        model.addAttribute("user", new User());
//        Set<Role> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles);
//        model.addAttribute("userAdmin", user);
//        return "add-user";
//    }

    @PostMapping(value ="/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
//        return "redirect:/admin";
    }


//    @PostMapping(value = "/deleteUser")
    @DeleteMapping("/{id}")
    public HttpStatus deleteUser(@PathVariable("id") Integer id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            userService.delete(byId.get());
        }
        return HttpStatus.OK;
//        return "redirect:/admin";
    }

    @GetMapping(value = "/findUser/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Integer id, ModelMap model) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            model.addAttribute("user", byId.get());
        }
        return new ResponseEntity<>(byId.orElse(null), HttpStatus.OK);
//        return "find-user-by-id";
    }


    @PutMapping(value = "/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody @Validated User user, @RequestParam("userId") Integer id) {
        userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
        //        return "redirect:/admin";
    }

}
