package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> allUsers();
    void addUser(User user, String role);
    void removeUser(long id);
    void editUser(User user, String role);
    Set<Role> getRoleForUser(String role);
}
