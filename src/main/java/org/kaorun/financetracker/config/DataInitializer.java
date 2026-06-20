package org.kaorun.financetracker.config;

import org.kaorun.financetracker.model.RoleEnum;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsernameContainingIgnoreCase("admin").isEmpty()) {

                UserModel admin = new UserModel();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setEmail("admin@finance.ru");
                admin.setNickname("Главный Администратор");
                admin.setActive(true);

                admin.setRoles(Set.of(RoleEnum.ADMIN, RoleEnum.USER));

                userRepository.save(admin);
            }
        };
    }
}