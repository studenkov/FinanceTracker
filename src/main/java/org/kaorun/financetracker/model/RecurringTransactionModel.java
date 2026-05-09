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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull(message = "Укажите категорию")
    private CategoryModel category;

    @NotNull(message = "Укажите сумму")
    private Double amount;

    @NotNull(message = "Укажите следующую дату")
    private LocalDate nextDate;

    @NotNull(message = "Укажите статус активности")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frequency_id")
    @NotNull(message = "Укажите частоту")
    private FrequencyModel frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @NotNull(message = "Укажите счет")
    private AccountModel account;

    public RecurringTransactionModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CategoryModel getCategory() { return category; }
    public void setCategory(CategoryModel category) { this.category = category; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDate getNextDate() { return nextDate; }
    public void setNextDate(LocalDate nextDate) { this.nextDate = nextDate; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public FrequencyModel getFrequency() { return frequency; }
    public void setFrequency(FrequencyModel frequency) { this.frequency = frequency; }
    public AccountModel getAccount() { return account; }
    public void setAccount(AccountModel account) { this.account = account; }
}