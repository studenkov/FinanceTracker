package org.kaorun.financetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "recurring_transactions")
public class RecurringTransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Укажите ID категории")
    private Integer categoryId;

    @NotNull(message = "Укажите сумму")
    private Double amount;

    @NotNull(message = "Укажите следующую дату")
    private LocalDate nextDate;

    @NotNull(message = "Укажите статус активности")
    private Boolean isActive;

    @NotNull(message = "Укажите ID частоты")
    private Integer frequencyId;

    @NotNull(message = "Укажите ID счета")
    private Integer accountId;

    public RecurringTransactionModel() {
    }

    public RecurringTransactionModel(Integer categoryId, Double amount, LocalDate nextDate, Boolean isActive, Integer frequencyId, Integer accountId) {
        this.categoryId = categoryId;
        this.amount = amount;
        this.nextDate = nextDate;
        this.isActive = isActive;
        this.frequencyId = frequencyId;
        this.accountId = accountId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getNextDate() { return nextDate; }
    public void setNextDate(LocalDate nextDate) { this.nextDate = nextDate; }
    public Boolean isActive() { return isActive; }
    public void setActive(Boolean active) { isActive = active; }
    public Integer getFrequencyId() { return frequencyId; }
    public void setFrequencyId(Integer frequencyId) { this.frequencyId = frequencyId; }
    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
}
