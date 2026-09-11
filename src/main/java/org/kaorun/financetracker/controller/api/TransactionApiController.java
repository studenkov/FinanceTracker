package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.TransactionModel;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Транзакции", description = "Учет и управление финансовыми операциями (доходы и расходы)")
public class TransactionApiController extends AbstractApiController<TransactionModel, Long> {

    public TransactionApiController(TransactionService transactionService) {
        super(transactionService, "Транзакция успешно удалена");
    }
}