package org.kaorun.financetracker.controller;

import jakarta.validation.Valid;
import org.kaorun.financetracker.model.*;
import org.kaorun.financetracker.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final int PAGE_SIZE = 10;

    private final UserService userService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final GoalService goalService;
    private final RoleService roleService;
    private final TypeService typeService;
    private final CurrencyService currencyService;
    private final FrequencyService frequencyService;
    private final RecurringTransactionService recurringTransactionService;

    public AdminController(UserService userService, AccountService accountService, CategoryService categoryService,
                           TransactionService transactionService, BudgetService budgetService, GoalService goalService,
                           RoleService roleService, TypeService typeService, CurrencyService currencyService,
                           FrequencyService frequencyService, RecurringTransactionService recurringTransactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.goalService = goalService;
        this.roleService = roleService;
        this.typeService = typeService;
        this.currencyService = currencyService;
        this.frequencyService = frequencyService;
        this.recurringTransactionService = recurringTransactionService;
    }

    private <T> Page<T> getPage(List<T> sourceList, int page) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, sourceList.size());
        if (start > end) {
            return new PageImpl<>(new ArrayList<>());
        }
        return new PageImpl<>(sourceList.subList(start, end), PageRequest.of(page, PAGE_SIZE), sourceList.size());
    }

    private void populateAdminModel(Model model,
                                    int usersPage, String usersQuery,
                                    int accountsPage, String accountsQuery,
                                    int categoriesPage, String categoriesQuery,
                                    int transactionsPage, String transactionsQuery,
                                    int budgetsPage, String budgetsQuery,
                                    int goalsPage, String goalsQuery,
                                    int rolesPage, String rolesQuery,
                                    int typesPage, String typesQuery,
                                    int currenciesPage, String currenciesQuery,
                                    int frequenciesPage, String frequenciesQuery,
                                    int recurringPage, String recurringQuery) {

        addEntityAttributes(model, "users", usersPage, usersQuery,
                query -> searchByIdOrText(query, userService::findById, userService::findByUsername),
                () -> userService.findPage(usersPage, PAGE_SIZE), userService::findAll);

        addEntityAttributes(model, "accounts", accountsPage, accountsQuery,
                query -> searchByIdOrText(query, accountService::findById, accountService::findByTitle),
                () -> accountService.findPage(accountsPage, PAGE_SIZE), accountService::findAll);

        addEntityAttributes(model, "categories", categoriesPage, categoriesQuery,
                query -> searchByIdOrText(query, categoryService::findById, categoryService::findByTitle),
                () -> categoryService.findPage(categoriesPage, PAGE_SIZE), categoryService::findAll);

        addEntityAttributes(model, "transactions", transactionsPage, transactionsQuery,
                query -> searchByIdOrText(query, transactionService::findById, transactionService::findByNote),
                () -> transactionService.findPage(transactionsPage, PAGE_SIZE), transactionService::findAll);

        addEntityAttributes(model, "budgets", budgetsPage, budgetsQuery,
                query -> searchByIdOrText(query, budgetService::findById, q -> budgetService.findByLimit(Double.parseDouble(q))),
                () -> budgetService.findPage(budgetsPage, PAGE_SIZE), budgetService::findAll);

        addEntityAttributes(model, "goals", goalsPage, goalsQuery,
                query -> searchByIdOrText(query, goalService::findById, goalService::findByTitle),
                () -> goalService.findPage(goalsPage, PAGE_SIZE), goalService::findAll);

        addEntityAttributes(model, "roles", rolesPage, rolesQuery,
                query -> searchByIdOrText(query, roleService::findById, roleService::findByRole),
                () -> roleService.findPage(rolesPage, PAGE_SIZE), roleService::findAll);

        addEntityAttributes(model, "types", typesPage, typesQuery,
                query -> searchByIdOrText(query, typeService::findById, typeService::findByTitle),
                () -> typeService.findPage(typesPage, PAGE_SIZE), typeService::findAll);

        addEntityAttributes(model, "currencies", currenciesPage, currenciesQuery,
                query -> searchByIdOrText(query, currencyService::findById, currencyService::findByTitle),
                () -> currencyService.findPage(currenciesPage, PAGE_SIZE), currencyService::findAll);

        addEntityAttributes(model, "frequencies", frequenciesPage, frequenciesQuery,
                query -> searchByIdOrText(query, frequencyService::findById, frequencyService::findByTitle),
                () -> frequencyService.findPage(frequenciesPage, PAGE_SIZE), frequencyService::findAll);

        addEntityAttributes(model, "recurringTransactions", recurringPage, recurringQuery,
                query -> searchByIdOrText(query, recurringTransactionService::findById, q -> recurringTransactionService.findByActive(Boolean.parseBoolean(q))),
                () -> recurringTransactionService.findPage(recurringPage, PAGE_SIZE), recurringTransactionService::findAll);
    }

    private <T> List<T> searchByIdOrText(String query,
                                         java.util.function.Function<Long, T> idSearcher,
                                         java.util.function.Function<String, List<T>> textSearcher) {
        try {
            Long id = Long.parseLong(query);
            T result = idSearcher.apply(id);

            List<T> resultList = new ArrayList<>();
            if (result != null) {
                resultList.add(result);
            }
            return resultList;
        } catch (NumberFormatException e) {
            return textSearcher.apply(query);
        }
    }

    @GetMapping
    public String adminPanel(Model model,
                             @RequestParam(defaultValue = "0") int usersPage, @RequestParam(required = false) String usersQuery,
                             @RequestParam(defaultValue = "0") int accountsPage, @RequestParam(required = false) String accountsQuery,
                             @RequestParam(defaultValue = "0") int categoriesPage, @RequestParam(required = false) String categoriesQuery,
                             @RequestParam(defaultValue = "0") int transactionsPage, @RequestParam(required = false) String transactionsQuery,
                             @RequestParam(defaultValue = "0") int budgetsPage, @RequestParam(required = false) String budgetsQuery,
                             @RequestParam(defaultValue = "0") int goalsPage, @RequestParam(required = false) String goalsQuery,
                             @RequestParam(defaultValue = "0") int rolesPage, @RequestParam(required = false) String rolesQuery,
                             @RequestParam(defaultValue = "0") int typesPage, @RequestParam(required = false) String typesQuery,
                             @RequestParam(defaultValue = "0") int currenciesPage, @RequestParam(required = false) String currenciesQuery,
                             @RequestParam(defaultValue = "0") int frequenciesPage, @RequestParam(required = false) String frequenciesQuery,
                             @RequestParam(defaultValue = "0") int recurringPage, @RequestParam(required = false) String recurringQuery) {

        populateAdminModel(model, usersPage, usersQuery, accountsPage, accountsQuery, categoriesPage, categoriesQuery,
                transactionsPage, transactionsQuery, budgetsPage, budgetsQuery, goalsPage, goalsQuery,
                rolesPage, rolesQuery, typesPage, typesQuery, currenciesPage, currenciesQuery,
                frequenciesPage, frequenciesQuery, recurringPage, recurringQuery);

        if (!model.containsAttribute("userModel")) model.addAttribute("userModel", new UserModel());
        if (!model.containsAttribute("accountModel")) model.addAttribute("accountModel", new AccountModel());
        if (!model.containsAttribute("categoryModel")) model.addAttribute("categoryModel", new CategoryModel());
        if (!model.containsAttribute("transactionModel")) model.addAttribute("transactionModel", new TransactionModel());
        if (!model.containsAttribute("budgetModel")) model.addAttribute("budgetModel", new BudgetModel());
        if (!model.containsAttribute("goalModel")) model.addAttribute("goalModel", new GoalModel());
        if (!model.containsAttribute("roleModel")) model.addAttribute("roleModel", new RoleModel());
        if (!model.containsAttribute("typeModel")) model.addAttribute("typeModel", new TypeModel());
        if (!model.containsAttribute("currencyModel")) model.addAttribute("currencyModel", new CurrencyModel());
        if (!model.containsAttribute("frequencyModel")) model.addAttribute("frequencyModel", new FrequencyModel());
        if (!model.containsAttribute("recurringTransactionModel")) model.addAttribute("recurringTransactionModel", new RecurringTransactionModel());

        return "admin";
    }

    private <T> void addEntityAttributes(Model model, String entityName, int page, String query, java.util.function.Function<String, List<T>> searcher, java.util.function.Supplier<List<T>> pager, java.util.function.Supplier<List<T>> allFinder) {
        Page<T> entityPage;
        if (query != null && !query.isEmpty()) {
            try {
                entityPage = getPage(searcher.apply(query), page);
            } catch (Exception e) {
                entityPage = new PageImpl<>(new ArrayList<>());
            }
        } else {
            entityPage = new PageImpl<>(pager.get(), PageRequest.of(page, PAGE_SIZE), allFinder.get().size());
        }

        if (!model.containsAttribute(entityName)) {
            model.addAttribute(entityName, entityPage.getContent());
        }
        model.addAttribute(entityName + "Page", page);
        model.addAttribute(entityName + "Query", query);
        model.addAttribute(entityName + "Total", entityPage.getTotalPages());
    }

    private String getRedirectURL(String entity, int page, String query) {
        return String.format("redirect:/admin?%sPage=%d%s#%s", entity, page, (query != null && !query.isEmpty() ? "&" + entity + "Query=" + query : ""), entity);
    }

    private boolean handleValidationError(BindingResult result, RedirectAttributes redirectAttributes, String org, String entityName, Object entity) {
        if (result.hasErrors()) {
            String specificErrors = result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(java.util.stream.Collectors.joining(" | "));

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + org, result);
            redirectAttributes.addFlashAttribute(org, entity);

            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка (" + entityName + "): " + specificErrors);
            return true;
        }
        return false;
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("userModel") UserModel user, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "userModel", "пользователя", user)) return getRedirectURL("users", 0, null);
        userService.add(user); return getRedirectURL("users", 0, null);
    }

    @PostMapping("/users/update")
    public String updateUser(@Valid @ModelAttribute("userModel") UserModel user, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "userModel", "пользователя", user)) return getRedirectURL("users", currentPage, query);
        userService.update(user); return getRedirectURL("users", currentPage, query);
    }

    @PostMapping("/users/delete") public String deleteUser(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { userService.delete(id); return getRedirectURL("users", currentPage, query); }

    @PostMapping("/accounts/add")
    public String addAccount(@Valid @ModelAttribute("accountModel") AccountModel account, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "accountModel", "счета", account)) return getRedirectURL("accounts", 0, null);
        accountService.add(account); return getRedirectURL("accounts", 0, null);
    }

    @PostMapping("/accounts/update")
    public String updateAccount(@Valid @ModelAttribute("accountModel") AccountModel account, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "accountModel", "счета", account)) return getRedirectURL("accounts", currentPage, query);
        accountService.update(account); return getRedirectURL("accounts", currentPage, query);
    }
    @PostMapping("/accounts/delete") public String deleteAccount(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { accountService.delete(id); return getRedirectURL("accounts", currentPage, query); }

    @PostMapping("/categories/add")
    public String addCategory(@Valid @ModelAttribute("categoryModel") CategoryModel category, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "categoryModel", "категории", category)) return getRedirectURL("categories", 0, null);
        categoryService.add(category); return getRedirectURL("categories", 0, null);
    }

    @PostMapping("/categories/update")
    public String updateCategory(@Valid @ModelAttribute("categoryModel") CategoryModel category, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "categoryModel", "категории", category)) return getRedirectURL("categories", currentPage, query);
        categoryService.update(category); return getRedirectURL("categories", currentPage, query);
    }
    @PostMapping("/categories/delete") public String deleteCategory(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { categoryService.delete(id); return getRedirectURL("categories", currentPage, query); }

    @PostMapping("/transactions/add")
    public String addTransaction(@Valid @ModelAttribute("transactionModel") TransactionModel transaction, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "transactionModel", "транзакции", transaction)) return getRedirectURL("transactions", 0, null);
        transactionService.add(transaction); return getRedirectURL("transactions", 0, null);
    }

    @PostMapping("/transactions/update")
    public String updateTransaction(@Valid @ModelAttribute("transactionModel") TransactionModel transaction, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "transactionModel", "транзакции", transaction)) return getRedirectURL("transactions", currentPage, query);
        transactionService.update(transaction); return getRedirectURL("transactions", currentPage, query);
    }
    @PostMapping("/transactions/delete") public String deleteTransaction(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { transactionService.delete(id); return getRedirectURL("transactions", currentPage, query); }

    @PostMapping("/budgets/add")
    public String addBudget(@Valid @ModelAttribute("budgetModel") BudgetModel budget, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "budgetModel", "бюджета", budget)) return getRedirectURL("budgets", 0, null);
        budgetService.add(budget); return getRedirectURL("budgets", 0, null);
    }

    @PostMapping("/budgets/update")
    public String updateBudget(@Valid @ModelAttribute("budgetModel") BudgetModel budget, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "budgetModel", "бюджета", budget)) return getRedirectURL("budgets", currentPage, query);
        budgetService.update(budget); return getRedirectURL("budgets", currentPage, query);
    }
    @PostMapping("/budgets/delete") public String deleteBudget(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { budgetService.delete(id); return getRedirectURL("budgets", currentPage, query); }

    @PostMapping("/goals/add")
    public String addGoal(@Valid @ModelAttribute("goalModel") GoalModel goal, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "goalModel", "цели", goal)) return getRedirectURL("goals", 0, null);
        goalService.add(goal); return getRedirectURL("goals", 0, null);
    }

    @PostMapping("/goals/update")
    public String updateGoal(@Valid @ModelAttribute("goalModel") GoalModel goal, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "goalModel", "цели", goal)) return getRedirectURL("goals", currentPage, query);
        goalService.update(goal); return getRedirectURL("goals", currentPage, query);
    }
    @PostMapping("/goals/delete") public String deleteGoal(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { goalService.delete(id); return getRedirectURL("goals", currentPage, query); }

    @PostMapping("/roles/add")
    public String addRole(@Valid @ModelAttribute("roleModel") RoleModel role, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "roleModel", "роли", role)) return getRedirectURL("roles", 0, null);
        roleService.add(role); return getRedirectURL("roles", 0, null);
    }

    @PostMapping("/roles/update")
    public String updateRole(@Valid @ModelAttribute("roleModel") RoleModel role, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "roleModel", "роли", role)) return getRedirectURL("roles", currentPage, query);
        roleService.update(role); return getRedirectURL("roles", currentPage, query);
    }
    @PostMapping("/roles/delete") public String deleteRole(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { roleService.delete(id); return getRedirectURL("roles", currentPage, query); }

    @PostMapping("/types/add")
    public String addType(@Valid @ModelAttribute("typeModel") TypeModel type, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "typeModel", "типа", type)) return getRedirectURL("types", 0, null);
        typeService.add(type); return getRedirectURL("types", 0, null);
    }

    @PostMapping("/types/update")
    public String updateType(@Valid @ModelAttribute("typeModel") TypeModel type, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "typeModel", "типа", type)) return getRedirectURL("types", currentPage, query);
        typeService.update(type); return getRedirectURL("types", currentPage, query);
    }
    @PostMapping("/types/delete") public String deleteType(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { typeService.delete(id); return getRedirectURL("types", currentPage, query); }

    @PostMapping("/currencies/add")
    public String addCurrency(@Valid @ModelAttribute("currencyModel") CurrencyModel currency, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "currencyModel", "валюты", currency)) return getRedirectURL("currencies", 0, null);
        currencyService.add(currency); return getRedirectURL("currencies", 0, null);
    }

    @PostMapping("/currencies/update")
    public String updateCurrency(@Valid @ModelAttribute("currencyModel") CurrencyModel currency, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "currencyModel", "валюты", currency)) return getRedirectURL("currencies", currentPage, query);
        currencyService.update(currency); return getRedirectURL("currencies", currentPage, query);
    }
    @PostMapping("/currencies/delete") public String deleteCurrency(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { currencyService.delete(id); return getRedirectURL("currencies", currentPage, query); }

    @PostMapping("/frequencies/add")
    public String addFrequency(@Valid @ModelAttribute("frequencyModel") FrequencyModel frequency, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "frequencyModel", "частоты", frequency)) return getRedirectURL("frequencies", 0, null);
        frequencyService.add(frequency); return getRedirectURL("frequencies", 0, null);
    }

    @PostMapping("/frequencies/update")
    public String updateFrequency(@Valid @ModelAttribute("frequencyModel") FrequencyModel frequency, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "frequencyModel", "частоты", frequency)) return getRedirectURL("frequencies", currentPage, query);
        frequencyService.update(frequency); return getRedirectURL("frequencies", currentPage, query);
    }
    @PostMapping("/frequencies/delete") public String deleteFrequency(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { frequencyService.delete(id); return getRedirectURL("frequencies", currentPage, query); }

    @PostMapping("/recurring/add")
    public String addRecurring(@Valid @ModelAttribute("recurringTransactionModel") RecurringTransactionModel recurring, BindingResult result, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "recurringTransactionModel", "повторяющейся транзакции", recurring)) return getRedirectURL("recurringTransactions", 0, null);
        recurringTransactionService.add(recurring); return getRedirectURL("recurringTransactions", 0, null);
    }

    @PostMapping("/recurring/update")
    public String updateRecurring(@Valid @ModelAttribute("recurringTransactionModel") RecurringTransactionModel recurring, BindingResult result, @RequestParam int currentPage, @RequestParam(required = false) String query, RedirectAttributes redirectAttrs) {
        if (handleValidationError(result, redirectAttrs, "recurringTransactionModel", "повторяющейся транзакции", recurring)) return getRedirectURL("recurringTransactions", currentPage, query);
        recurringTransactionService.update(recurring); return getRedirectURL("recurringTransactions", currentPage, query);
    }
    @PostMapping("/recurring/delete") public String deleteRecurring(@RequestParam Long id, @RequestParam int currentPage, @RequestParam(required = false) String query) { recurringTransactionService.delete(id); return getRedirectURL("recurringTransactions", currentPage, query); }
}