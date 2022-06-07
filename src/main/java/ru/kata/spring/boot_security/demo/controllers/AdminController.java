package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String allUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "addUser";
    }

    @PostMapping("/addUserToDB")
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(required=false) String roleAdmin,
                          @RequestParam(required=false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("removeUser/{id}")
    public String removeUser(@PathVariable("id")long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable("id")long id, ModelMap modelMap) {
        User user =userService.getById(id);
        Set<Role> roles = user.getRoles();
        for(Role role: roles) {
            if(role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                modelMap.addAttribute("roleAdmin", true);
            }
            if (role.equals(roleService.getRoleByName("ROLE_USER"))) {
                modelMap.addAttribute("roleUser", true);
            }
        }
        modelMap.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUserInDB")
    public String postEditUser(@ModelAttribute("user") User user,
                               @RequestParam(required=false) String roleAdmin,
                               @RequestParam(required=false) String roleUser) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin != null && roleAdmin .equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (roleUser != null && roleUser.equals("ROLE_USER")) {
            roles.add(roleService.getRoleByName("ROLE_USER"));
        }
        user.setRoles(roles);
        userService.editUser(user);
        return "redirect:/admin";
    }

}
