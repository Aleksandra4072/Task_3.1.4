package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    List<User> allUsers();
    void addUser(User user);
    void removeUser(Long id);
    List<Role> allRoles();
    void saveAndFlush(User user);
    Role findRoleById(Long roleId);
    Set<Role> setRole(Long[] roleChoice);
    Set<Role> setRoleForEdition(Long[] roleChoice, User user);
}
