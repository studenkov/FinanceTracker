package org.kaorun.financetracker.config;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.model.*;
import org.kaorun.financetracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CurrencyRepository currencyRepository;
    private final TypeRepository typeRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final GoalRepository goalRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 1. Roles
            if (roleRepository.count() == 0) {
                roleRepository.save(new RoleModel(null, "ROLE_ADMIN"));
                roleRepository.save(new RoleModel(null, "ROLE_MANAGER"));
                roleRepository.save(new RoleModel(null, "ROLE_USER"));
            }

            // 2. Users (Admin, Manager, User)
            UserModel admin = userRepository.findByUsernameIgnoreCase("admin")
                    .orElseGet(() -> UserModel.builder().username("admin").build());
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@finance.ru");
            admin.setNickname("Главный Администратор");
            admin.setActive(true);
            admin.setRoles(Set.of(RoleEnum.ADMIN, RoleEnum.MANAGER, RoleEnum.USER));
            admin = userRepository.save(admin);
            final UserModel adminUser = admin;

            UserModel manager = userRepository.findByUsernameIgnoreCase("manager")
                    .orElseGet(() -> UserModel.builder().username("manager").build());
            manager.setPassword(passwordEncoder.encode("manager"));
            manager.setEmail("manager@finance.ru");
            manager.setNickname("Финансовый Менеджер");
            manager.setActive(true);
            manager.setRoles(Set.of(RoleEnum.MANAGER, RoleEnum.USER));
            userRepository.save(manager);

            UserModel regularUser = userRepository.findByUsernameIgnoreCase("user")
                    .orElseGet(() -> UserModel.builder().username("user").build());
            regularUser.setPassword(passwordEncoder.encode("user"));
            regularUser.setEmail("user@finance.ru");
            regularUser.setNickname("Обычный Пользователь");
            regularUser.setActive(true);
            regularUser.setRoles(Set.of(RoleEnum.USER));
            userRepository.save(regularUser);

            // 3. Currencies
            CurrencyModel rub = currencyRepository.findAll().stream()
                    .filter(c -> "RUB".equalsIgnoreCase(c.getTitle()) || "Рубль".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> currencyRepository.save(new CurrencyModel("RUB")));

            CurrencyModel usd = currencyRepository.findAll().stream()
                    .filter(c -> "USD".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> currencyRepository.save(new CurrencyModel("USD")));

            // 4. Types
            TypeModel incomeType = typeRepository.findAll().stream()
                    .filter(t -> "Доход".equalsIgnoreCase(t.getTitle()))
                    .findFirst()
                    .orElseGet(() -> typeRepository.save(new TypeModel("Доход")));

            TypeModel expenseType = typeRepository.findAll().stream()
                    .filter(t -> "Расход".equalsIgnoreCase(t.getTitle()))
                    .findFirst()
                    .orElseGet(() -> typeRepository.save(new TypeModel("Расход")));

            // 5. Categories
            CategoryModel catSalary = categoryRepository.findAll().stream()
                    .filter(c -> "Зарплата".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(CategoryModel.builder()
                            .title("Зарплата")
                            .type(incomeType)
                            .user(adminUser)
                            .build()));

            CategoryModel catFreelance = categoryRepository.findAll().stream()
                    .filter(c -> "Фриланс".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(CategoryModel.builder()
                            .title("Фриланс")
                            .type(incomeType)
                            .user(adminUser)
                            .build()));

            CategoryModel catGroceries = categoryRepository.findAll().stream()
                    .filter(c -> "Продукты".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(CategoryModel.builder()
                            .title("Продукты")
                            .type(expenseType)
                            .user(adminUser)
                            .build()));

            CategoryModel catTransport = categoryRepository.findAll().stream()
                    .filter(c -> "Транспорт".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(CategoryModel.builder()
                            .title("Транспорт")
                            .type(expenseType)
                            .user(adminUser)
                            .build()));

            CategoryModel catCafe = categoryRepository.findAll().stream()
                    .filter(c -> "Кафе и рестораны".equalsIgnoreCase(c.getTitle()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(CategoryModel.builder()
                            .title("Кафе и рестораны")
                            .type(expenseType)
                            .user(adminUser)
                            .build()));

            // 8. Accounts
            AccountModel mainAccount = accountRepository.findAll().stream()
                    .filter(a -> "Основная карта".equalsIgnoreCase(a.getTitle()))
                    .findFirst()
                    .orElseGet(() -> accountRepository.save(AccountModel.builder()
                            .title("Основная карта")
                            .balance(847500.0)
                            .currency(rub)
                            .user(adminUser)
                            .build()));

            AccountModel savingsAccount = accountRepository.findAll().stream()
                    .filter(a -> "Накопления".equalsIgnoreCase(a.getTitle()))
                    .findFirst()
                    .orElseGet(() -> accountRepository.save(AccountModel.builder()
                            .title("Накопления")
                            .balance(300000.0)
                            .currency(rub)
                            .user(adminUser)
                            .build()));

            AccountModel investAccount = accountRepository.findAll().stream()
                    .filter(a -> "Инвестиции".equalsIgnoreCase(a.getTitle()))
                    .findFirst()
                    .orElseGet(() -> accountRepository.save(AccountModel.builder()
                            .title("Инвестиции")
                            .balance(100000.0)
                            .currency(rub)
                            .user(adminUser)
                            .build()));

            // 9. Budgets
            if (budgetRepository.count() == 0) {
                LocalDate now = LocalDate.now();
                LocalDate start = now.withDayOfMonth(1);
                LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

                budgetRepository.save(BudgetModel.builder()
                        .category(catGroceries)
                        .limitAmount(50000.0)
                        .startDate(start)
                        .endDate(end)
                        .build());

                budgetRepository.save(BudgetModel.builder()
                        .category(catTransport)
                        .limitAmount(15000.0)
                        .startDate(start)
                        .endDate(end)
                        .build());
            }

            // 10. Goals
            if (goalRepository.count() == 0) {
                goalRepository.save(GoalModel.builder()
                        .title("Отпуск в Турции")
                        .targetAmount(250000.0)
                        .currentAmount(180000.0)
                        .account(savingsAccount)
                        .build());

                goalRepository.save(GoalModel.builder()
                        .title("Новый ноутбук")
                        .targetAmount(150000.0)
                        .currentAmount(95000.0)
                        .account(savingsAccount)
                        .build());
            }

            // 9. Sample Transactions
            if (transactionRepository.count() == 0) {
                LocalDate today = LocalDate.now();
                transactionRepository.save(TransactionModel.builder()
                        .category(catSalary)
                        .account(mainAccount)
                        .date(today.minusDays(2))
                        .amount(180000.0)
                        .note("Аванс за текущий месяц")
                        .build());

                transactionRepository.save(TransactionModel.builder()
                        .category(catGroceries)
                        .account(mainAccount)
                        .date(today.minusDays(1))
                        .amount(3450.0)
                        .note("Продукты в супермаркете")
                        .build());

                transactionRepository.save(TransactionModel.builder()
                        .category(catCafe)
                        .account(mainAccount)
                        .date(today)
                        .amount(890.0)
                        .note("Кофе и круассан")
                        .build());
            }
        };
    }
}