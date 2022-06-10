package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserRepository{
    List<User> allUsers();
    void addUser(User user);
    void removeUser(Long id);
    void editUser(User user);
    User getById(Long id);
    User getByUsername(String username);
}
