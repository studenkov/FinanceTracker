package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.GoalModel;
import org.kaorun.financetracker.service.GoalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/goals")
@Tag(name = "Цели", description = "Управление финансовыми целями и накоплениями")
public class GoalApiController extends AbstractApiController<GoalModel, Long> {

    public GoalApiController(GoalService goalService) {
        super(goalService, "Цель успешно удалена");
    }
}