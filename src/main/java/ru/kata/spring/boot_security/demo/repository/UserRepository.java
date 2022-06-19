package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;


public interface UserRepository{
    List<User> allUsers();
    void addUser(User user);
    void removeUser(long id);
    void editUser(User user);
    User getByUsername(String username);
}
