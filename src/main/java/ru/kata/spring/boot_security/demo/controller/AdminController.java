package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "admin/allUsers")
    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userService.allUsers(), HttpStatus.OK);
    }

   @PostMapping(value = "/admin/removeUser")
    public void removeUser(@RequestParam Long id) {
        userService.removeUser(id);
    }

    @PostMapping(value = "/admin/editUser")
    public String editUser(@ModelAttribute User user,
                           @RequestParam(value = "roleChoice", required = false) Long[] roleChoice) {
        Set<Role> roles = new HashSet<>();
        if (roleChoice != null) {
            for (long i : roleChoice) {
                roles.add(userService.findRoleById(i));
            }
        } else {
            roles.add(userService.findRoleById(2L));
        }
        user.setRoles(roles);
        userService.saveAndFlush(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute User user,
                          @RequestParam(value = "roleChoice", required = false) Long[] roleChoice) {
        Set<Role> roles = new HashSet<>();
        if (roleChoice != null) {
            for (long i : roleChoice) {
                roles.add(userService.findRoleById(i));
            }
        } else {
            roles.add(userService.findRoleById(2L));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping(value = "user/getUser")
    public ResponseEntity<List<User>> getUser(HttpSession session) {
        List<User> users = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        users.add(user);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
