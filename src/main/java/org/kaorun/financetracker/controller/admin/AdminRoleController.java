package org.kaorun.financetracker.controller.admin;

import org.kaorun.financetracker.model.RoleEnum;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRoleController {

    private final UserService userService;

    public AdminRoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model,
                            @RequestParam(required = false) String query,
                            @RequestParam(defaultValue = "0") int page) {

        List<UserModel> users;

        if (query != null && !query.trim().isEmpty()) {
            users = userService.findByUsername(query.trim());
        } else {
            users = userService.findAll();
        }

        int PAGE_SIZE = 10;
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, users.size());
        List<UserModel> pageContent = (start <= end && start < users.size()) ? users.subList(start, end) : List.of();
        Page<UserModel> data = new PageImpl<>(pageContent, PageRequest.of(page, PAGE_SIZE), users.size());

        model.addAttribute("data", data);
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "roles");

        return "admin/roles";
    }

    @PostMapping("/update")
    public String updateRolesPost(@RequestParam Long id,
                                  @RequestParam(name="roles[]", required = false) String[] roles,
                                  @RequestParam(defaultValue = "0") int currentPage,
                                  @RequestParam(required = false) String query) {

        UserModel user = userService.findById(id);
        user.getRoles().clear();

        if (roles != null) {
            for (String role : roles) {
                user.getRoles().add(RoleEnum.valueOf(role));
            }
        }
        userService.update(user);

        String redirectUrl = "redirect:/admin/roles?page=" + currentPage;
        if (query != null && !query.trim().isEmpty()) {
            redirectUrl += "&query=" + query;
        }

        return redirectUrl;
    }
}