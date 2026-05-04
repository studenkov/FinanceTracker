package org.kaorun.financetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class AccountModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Поле не может быть пустым")
    private String title;

    @NotNull(message = "Укажите баланс")
    private Double balance;

    @NotNull(message = "Укажите ID пользователя")
    private Integer userId;

    @NotNull(message = "Укажите ID валюты")
    private Integer currencyId;

    public AccountModel() {
    }

    public AccountModel(String title, Double balance, Integer userId, Integer currencyId) {
        this.title = title;
        this.balance = balance;
        this.userId = userId;
        this.currencyId = currencyId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getCurrencyId() { return currencyId; }
    public void setCurrencyId(Integer currencyId) { this.currencyId = currencyId; }
}