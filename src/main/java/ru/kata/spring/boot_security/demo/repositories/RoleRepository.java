package ru.kata.spring.boot_security.demo.repositories;

import ru.kata.spring.boot_security.demo.models.Role;

public interface RoleRepository {
    Role getRoleByName(String name);
}
