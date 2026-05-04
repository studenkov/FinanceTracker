package org.kaorun.financetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Укажите ID категории")
    private Integer categoryId;

    @NotNull(message = "Укажите дату")
    private LocalDate date;

    private String note;

    @NotNull(message = "Укажите сумму")
    private Double amount;

    @NotNull(message = "Укажите ID счета")
    private Integer accountId;

    public TransactionModel() {
    }

    public TransactionModel(Integer categoryId, LocalDate date, String note, Double amount, Integer accountId) {
        this.categoryId = categoryId;
        this.date = date;
        this.note = note;
        this.amount = amount;
        this.accountId = accountId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public Integer getAccountId() { return accountId; }
    public void setAccountId(Integer accountId) { this.accountId = accountId; }
}
