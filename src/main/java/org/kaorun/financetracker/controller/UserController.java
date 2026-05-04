package org.kaorun.financetracker.controller;

import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String getAll(Model model) {
        model.addAttribute("users", service.findAll());
        return "userList";
    }

    @GetMapping("/users/page")
    public String getPage(@RequestParam int page, Model model) {
        model.addAttribute("users", service.findPage(page, 10));
        return "userList";
    }

    @GetMapping("/users/search")
    public String search(@RequestParam String username, Model model) {
        model.addAttribute("users", service.findByUsername(username));
        return "userList";
    }

    @PostMapping("/users/add")
    public String add(@RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String email,
                      @RequestParam String nickname,
                      @RequestParam int roleId) {
        service.add(new UserModel(username, password, email, nickname, roleId));
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String delete(@RequestParam long id) {
        service.delete(id);
        return "redirect:/users";
    }
}
