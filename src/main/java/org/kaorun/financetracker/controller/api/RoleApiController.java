package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.RoleModel;
import org.kaorun.financetracker.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Роли пользователей", description = "Справочник системных ролей пользователей")
public class RoleApiController extends AbstractApiController<RoleModel, Long> {

    public RoleApiController(RoleService roleService) {
        super(roleService, "Роль успешно удалена");
    }
}
