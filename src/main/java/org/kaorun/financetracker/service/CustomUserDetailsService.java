package org.kaorun.financetracker.service;

import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserModel> users = userRepository.findByUsernameContainingIgnoreCase(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        UserModel user = users.get(0);

        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}