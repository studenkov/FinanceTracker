package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.kaorun.financetracker.model.AccountModel;
import org.kaorun.financetracker.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Счета")
public class AccountApiController {
    private final AccountService accountService;

    public AccountApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @Operation(summary = "Получить все счета")
    public List<AccountModel> getAll() {
        return accountService.findAll();
    }

    @PostMapping
    @Operation(summary = "Создать новый счет")
    @ApiResponse(responseCode = "200", description = "Успешно создано")
    public ResponseEntity<AccountModel> create(@Valid @RequestBody AccountModel account) {
        accountService.add(account);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить счет по ID")
    @ApiResponse(responseCode = "200", description = "Успешно обновлено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AccountModel account) {
        if (accountService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        account.setId(id);
        accountService.add(account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить счет по ID")
    @ApiResponse(responseCode = "200", description = "Успешно удалено")
    @ApiResponse(responseCode = "404", description = "Запись не найдена")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (accountService.findById(id) == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Запись с ID " + id + " не найдена"));
        }
        accountService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Счет успешно удален"));
    }
}