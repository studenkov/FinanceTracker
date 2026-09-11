package org.kaorun.financetracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сводная финансовая статистика для дашборда")
public class DashboardStatsDto {

    @Schema(description = "Общий баланс по всем счетам", example = "1247500.00")
    private Double totalBalance;

    @Schema(description = "Доход за текущий месяц", example = "350000.00")
    private Double monthlyIncome;

    @Schema(description = "Расход за текущий месяц", example = "198450.00")
    private Double monthlyExpense;

    @Schema(description = "Накопления (доход минус расход)", example = "151550.00")
    private Double netSavings;

    @Schema(description = "Количество операций за текущий месяц", example = "42")
    private Long monthlyCount;

    @Schema(description = "Средний чек расхода за текущий месяц", example = "1562.00")
    private Double averageExpense;

    @Schema(description = "Список счетов пользователя")
    private List<AccountItemDto> accounts;

    @Schema(description = "Список последних операций")
    private List<TransactionItemDto> recentTransactions;

    @Schema(description = "Состояние бюджетов с прогрессом")
    private List<BudgetItemDto> budgets;

    @Schema(description = "Финансовые цели с процентом достижения")
    private List<GoalItemDto> goals;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Краткая информация о счете")
    public static class AccountItemDto {
        private Long id;
        private String title;
        private Double balance;
        private String currency;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Краткая информация о транзакции")
    public static class TransactionItemDto {
        private Long id;
        private LocalDate date;
        private Double amount;
        private String categoryTitle;
        private boolean income;
        private String accountTitle;
        private String note;
        private List<String> tags;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Информация о бюджете")
    public static class BudgetItemDto {
        private Long id;
        private String categoryTitle;
        private Double limitAmount;
        private Double spentAmount;
        private Double percentage;
        private boolean exceeded;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Информация о цели")
    public static class GoalItemDto {
        private Long id;
        private String title;
        private Double targetAmount;
        private Double currentAmount;
        private Double percentage;
        private String accountTitle;
    }
}
