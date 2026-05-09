package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController extends AbstractAdminController {
    private final UserService service;

    public AdminUserController(UserService service) { this.service = service; }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String query,
            Model model
    ) {
        model.addAttribute(
                "data",
                resolveData(
                        query,
                        page,
                        service::findById,
                        service::findByUsername,
                        () -> service.findPage(page, PAGE_SIZE),
                        service::findAll
                )
        );
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "users");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new UserModel());
        }
        return "admin/users";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") UserModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "пользователя", entity)) {
            return redirect("/admin/users", 0, null);
        }
        service.add(entity);
        return redirect("/admin/users", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") UserModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "пользователя", entity)) {
            return redirect("/admin/users", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/users", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/users", currentPage, query);
    }
}