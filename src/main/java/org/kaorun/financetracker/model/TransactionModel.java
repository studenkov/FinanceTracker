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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull(message = "Укажите категорию")
    private CategoryModel category;

    @NotNull(message = "Укажите дату")
    private LocalDate date;

    private String note;

    @NotNull(message = "Укажите сумму")
    private Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @NotNull(message = "Укажите счет")
    private AccountModel account;

    public TransactionModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CategoryModel getCategory() { return category; }
    public void setCategory(CategoryModel category) { this.category = category; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public AccountModel getAccount() { return account; }
    public void setAccount(AccountModel account) { this.account = account; }
}