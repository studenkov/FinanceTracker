package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.BudgetModel;
import org.kaorun.financetracker.service.BudgetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budgets")
@Tag(name = "Бюджеты", description = "Управление месячными лимитами расходов по категориям")
public class BudgetApiController extends AbstractApiController<BudgetModel, Long> {

    public BudgetApiController(BudgetService budgetService) {
        super(budgetService, "Бюджет успешно удален");
    }
}