package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Управление пользователями системы")
public class UserApiController extends AbstractApiController<UserModel, Long> {

    public UserApiController(UserService userService) {
        super(userService, "Пользователь успешно удален");
    }
}
