package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    void addUser(User user);
    void removeUser(long id);
    void editUser(User user);
    User getById(long id);
    User getByUsername(String username);
}
