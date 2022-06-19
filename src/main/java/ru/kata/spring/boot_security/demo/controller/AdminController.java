package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


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
    public void editUser(@RequestParam Long id, String username, String password, String firstName, String lastName, String email, String role) {
        userService.editUser(new User(id, username, password, firstName, lastName, email), role);
    }

    @PostMapping(value = "/admin/addUser")
    public void addUser(@RequestParam Long id, String username, String password, String firstName, String lastName, String email, String role){
        userService.addUser(new User(id, username, password, firstName, lastName, email), role);
    }

    @PostMapping(value = "user/getUser")
    public ResponseEntity<List<User>> getUser(HttpSession session) {
        List<User> users = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        users.add(user);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
