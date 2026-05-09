package org.kaorun.financetracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class BudgetModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Укажите лимит")
    private Double limitAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull(message = "Укажите категорию")
    private CategoryModel category;

    @NotNull(message = "Укажите дату начала")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Укажите дату окончания")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public BudgetModel() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getLimitAmount() { return limitAmount; }
    public void setLimitAmount(Double limitAmount) { this.limitAmount = limitAmount; }
    public CategoryModel getCategory() { return category; }
    public void setCategory(CategoryModel category) { this.category = category; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}