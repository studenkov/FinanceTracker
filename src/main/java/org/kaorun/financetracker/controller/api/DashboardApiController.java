package org.kaorun.financetracker.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.dto.DashboardStatsDto;
import org.kaorun.financetracker.dto.QuickTransactionRequest;
import org.kaorun.financetracker.model.*;
import org.kaorun.financetracker.repository.*;
import org.kaorun.financetracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Дашборд и Главная аналитика", description = "Основной API для сводной аналитики, баланса и быстрого проведения операций")
@RequiredArgsConstructor
public class DashboardApiController {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final GoalRepository goalRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionService transactionService;

    @GetMapping("/stats")
    @Operation(summary = "Получить сводную статистику дашборда", description = "Возвращает баланс, доходы, расходы, бюджеты и цели в реальном времени")
    @ApiResponse(responseCode = "200", description = "Успешно получена аналитика")
    public ResponseEntity<DashboardStatsDto> getStats() {
        List<AccountModel> accounts = accountRepository.findAll();
        List<TransactionModel> allTransactions = transactionRepository.findAll();
        List<BudgetModel> budgets = budgetRepository.findAll();
        List<GoalModel> goals = goalRepository.findAll();

        double totalBalance = accounts.stream()
                .mapToDouble(a -> a.getBalance() != null ? a.getBalance() : 0.0)
                .sum();

        YearMonth currentMonth = YearMonth.now();
        LocalDate startOfMonth = currentMonth.atDay(1);
        LocalDate endOfMonth = currentMonth.atEndOfMonth();

        double monthlyIncome = 0.0;
        double monthlyExpense = 0.0;
        long monthlyCount = 0;
        long expenseCount = 0;

        for (TransactionModel tx : allTransactions) {
            LocalDate date = tx.getDate();
            if (date != null && !date.isBefore(startOfMonth) && !date.isAfter(endOfMonth)) {
                monthlyCount++;
                boolean isIncome = isIncomeTransaction(tx);
                double amount = tx.getAmount() != null ? tx.getAmount() : 0.0;
                if (isIncome) {
                    monthlyIncome += amount;
                } else {
                    monthlyExpense += amount;
                    expenseCount++;
                }
            }
        }

        double netSavings = monthlyIncome - monthlyExpense;
        double averageExpense = expenseCount > 0 ? (monthlyExpense / expenseCount) : 0.0;

        List<DashboardStatsDto.AccountItemDto> accountDtos = accounts.stream()
                .map(a -> DashboardStatsDto.AccountItemDto.builder()
                        .id(a.getId())
                        .title(a.getTitle())
                        .balance(a.getBalance() != null ? a.getBalance() : 0.0)
                        .currency(a.getCurrency() != null ? a.getCurrency().getTitle() : "₽")
                        .build())
                .collect(Collectors.toList());

        List<DashboardStatsDto.TransactionItemDto> recentTxDtos = allTransactions.stream()
                .sorted(Comparator.comparing(TransactionModel::getDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .limit(8)
                .map(tx -> DashboardStatsDto.TransactionItemDto.builder()
                        .id(tx.getId())
                        .date(tx.getDate())
                        .amount(tx.getAmount())
                        .categoryTitle(tx.getCategory() != null ? tx.getCategory().getTitle() : "Без категории")
                        .income(isIncomeTransaction(tx))
                        .accountTitle(tx.getAccount() != null ? tx.getAccount().getTitle() : "Основной счет")
                        .note(tx.getNote() != null ? tx.getNote() : "")
                        .tags(List.of())
                        .build())
                .collect(Collectors.toList());

        List<DashboardStatsDto.BudgetItemDto> budgetDtos = budgets.stream().map(b -> {
            CategoryModel cat = b.getCategory();
            double spent = 0.0;
            if (cat != null) {
                spent = allTransactions.stream()
                        .filter(tx -> tx.getCategory() != null && Objects.equals(tx.getCategory().getId(), cat.getId()))
                        .filter(tx -> {
                            LocalDate d = tx.getDate();
                            if (d == null) return false;
                            if (b.getStartDate() != null && d.isBefore(b.getStartDate())) return false;
                            if (b.getEndDate() != null && d.isAfter(b.getEndDate())) return false;
                            return true;
                        })
                        .mapToDouble(tx -> tx.getAmount() != null ? tx.getAmount() : 0.0)
                        .sum();
            }
            double limit = b.getLimitAmount() != null && b.getLimitAmount() > 0 ? b.getLimitAmount() : 1.0;
            double pct = Math.min((spent / limit) * 100.0, 100.0);
            return DashboardStatsDto.BudgetItemDto.builder()
                    .id(b.getId())
                    .categoryTitle(cat != null ? cat.getTitle() : "Категория")
                    .limitAmount(b.getLimitAmount())
                    .spentAmount(spent)
                    .percentage(Math.round(pct * 10.0) / 10.0)
                    .exceeded(spent > limit)
                    .build();
        }).collect(Collectors.toList());

        List<DashboardStatsDto.GoalItemDto> goalDtos = goals.stream().map(g -> {
            double target = g.getTargetAmount() != null && g.getTargetAmount() > 0 ? g.getTargetAmount() : 1.0;
            double current = g.getCurrentAmount() != null ? g.getCurrentAmount() : 0.0;
            double pct = Math.min((current / target) * 100.0, 100.0);
            return DashboardStatsDto.GoalItemDto.builder()
                    .id(g.getId())
                    .title(g.getTitle())
                    .targetAmount(g.getTargetAmount())
                    .currentAmount(current)
                    .percentage(Math.round(pct * 10.0) / 10.0)
                    .accountTitle(g.getAccount() != null ? g.getAccount().getTitle() : "")
                    .build();
        }).collect(Collectors.toList());

        DashboardStatsDto stats = DashboardStatsDto.builder()
                .totalBalance(Math.round(totalBalance * 100.0) / 100.0)
                .monthlyIncome(Math.round(monthlyIncome * 100.0) / 100.0)
                .monthlyExpense(Math.round(monthlyExpense * 100.0) / 100.0)
                .netSavings(Math.round(netSavings * 100.0) / 100.0)
                .monthlyCount(monthlyCount)
                .averageExpense(Math.round(averageExpense * 100.0) / 100.0)
                .accounts(accountDtos)
                .recentTransactions(recentTxDtos)
                .budgets(budgetDtos)
                .goals(goalDtos)
                .build();

        return ResponseEntity.ok(stats);
    }

    @PostMapping("/quick-transaction")
    @Operation(summary = "Быстрое проведение операции", description = "Создает транзакцию через API и автоматически обновляет баланс связанного счета")
    @ApiResponse(responseCode = "200", description = "Транзакция успешно проведена")
    public ResponseEntity<TransactionModel> quickTransaction(@Valid @RequestBody QuickTransactionRequest request) {
        AccountModel account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Счет с ID " + request.getAccountId() + " не найден"));
        CategoryModel category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория с ID " + request.getCategoryId() + " не найдена"));

        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();

        TransactionModel transaction = TransactionModel.builder()
                .account(account)
                .category(category)
                .amount(request.getAmount())
                .date(date)
                .note(request.getNote())
                .build();

        TransactionModel saved = transactionService.add(transaction);
        return ResponseEntity.ok(saved);
    }

    private boolean isIncomeTransaction(TransactionModel tx) {
        if (tx.getCategory() != null && tx.getCategory().getType() != null) {
            String title = tx.getCategory().getType().getTitle();
            return title != null && title.toLowerCase().contains("доход");
        }
        return false;
    }
}
