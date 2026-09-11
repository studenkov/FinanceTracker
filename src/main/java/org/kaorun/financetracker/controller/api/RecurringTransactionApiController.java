package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.RecurringTransactionModel;
import org.kaorun.financetracker.service.RecurringTransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recurring")
@Tag(name = "Регулярные платежи", description = "Управление повторяющимися транзакциями и автоплатежами")
public class RecurringTransactionApiController extends AbstractApiController<RecurringTransactionModel, Long> {

    public RecurringTransactionApiController(RecurringTransactionService service) {
        super(service, "Регулярный платеж успешно удален");
    }
}
