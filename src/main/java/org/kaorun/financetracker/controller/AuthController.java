package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.RoleEnum;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registerView(Model model) {
        model.addAttribute("user", new UserModel());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(UserModel user, Model model) {
        if (!userService.findByUsername(user.getUsername()).isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);

        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "accessDenied"; // Страница из задания на 3
    }
}