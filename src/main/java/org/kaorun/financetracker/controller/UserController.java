package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("users", service.findPage(page, 10));
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserModel());
        }
        return "userList";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("user") UserModel user, BindingResult result) {
        if (!result.hasErrors()) {
            service.add(user);
        }
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/users";
    }
}