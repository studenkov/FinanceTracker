package org.kaorun.financetracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на быстрое добавление транзакции через API")
public class QuickTransactionRequest {

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма должна быть больше нуля")
    @Schema(description = "Сумма транзакции", example = "1500.00")
    private Double amount;

    @NotNull(message = "Счет обязателен")
    @Schema(description = "ID счета", example = "1")
    private Long accountId;

    @NotNull(message = "Категория обязательна")
    @Schema(description = "ID категории", example = "2")
    private Long categoryId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Schema(description = "Дата операции (по умолчанию сегодняшняя)", example = "2026-09-11")
    private LocalDate date;

    @Schema(description = "Заметка / описание", example = "Покупка продуктов")
    private String note;
}
