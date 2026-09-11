package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Счета", description = "Управление банковскими и наличными счетами пользователей")
public class AccountApiController extends AbstractApiController<AccountModel, Long> {

    public AccountApiController(AccountService accountService) {
        super(accountService, "Счет успешно удален");
    }
}