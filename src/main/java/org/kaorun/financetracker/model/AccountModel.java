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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Укажите пользователя")
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    @NotNull(message = "Укажите валюту")
    private CurrencyModel currency;

    public AccountModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }
    public CurrencyModel getCurrency() { return currency; }
    public void setCurrency(CurrencyModel currency) { this.currency = currency; }
}