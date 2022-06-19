package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> allUsers() {
        return userRepository.allUsers();
    }

    @Override
    @Transactional
    public void addUser(User user, String role) {
        user.setRoles(getRoleForUser(role));
        userRepository.addUser(user);
    }

    @Override
    @Transactional
    public Set<Role> getRoleForUser(String role) {
        Set<Role> roles = new HashSet<>();
        try {
            String[] partsRole = role.split(",");
            roles.add(new Role(partsRole[1]));
            roles.add(new Role(partsRole[0]));
            return roles;
        } catch (Exception e) {
            e.printStackTrace();
        }
        roles.add(new Role(role));
        return roles;
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userRepository.removeUser(id);
    }

    @Override
    @Transactional
    public void editUser(User user, String role) {
        user.setRoles(getRoleForUser(role));
        userRepository.editUser(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userMayBy = Optional.ofNullable(userRepository.getByUsername(username));
        return userMayBy.orElseThrow(IllegalAccessError::new);
    }
}

