package org.kaorun.financetracker.controller.admin;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.RoleModel;
import org.kaorun.financetracker.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
public class AdminRoleController extends AbstractAdminController {
    private final RoleService service;

    public AdminRoleController(RoleService service) { this.service = service; }

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
                        service::findByRole,
                        () -> service.findPage(page, PAGE_SIZE),
                        service::findAll
                )
        );
        model.addAttribute("query", query);
        model.addAttribute("activeTab", "roles");
        if (!model.containsAttribute("entityModel")) {
            model.addAttribute("entityModel", new RoleModel());
        }
        return "admin/roles";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute("entityModel") RoleModel entity,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "роли", entity)) {
            return redirect("/admin/roles", 0, null);
        }
        service.add(entity);
        return redirect("/admin/roles", 0, null);
    }

    @PostMapping("/update")
    public String update(
            @Valid @ModelAttribute("entityModel") RoleModel entity,
            BindingResult result,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query,
            RedirectAttributes redirectAttrs
    ) {
        if (hasErrors(result, redirectAttrs, "роли", entity)) {
            return redirect("/admin/roles", currentPage, query);
        }
        service.update(entity);
        return redirect("/admin/roles", currentPage, query);
    }

    @PostMapping("/delete")
    public String delete(
            @RequestParam Long id,
            @RequestParam int currentPage,
            @RequestParam(required = false) String query
    ) {
        service.delete(id);
        return redirect("/admin/roles", currentPage, query);
    }
}