package org.kaorun.financetracker.service;

import lombok.RequiredArgsConstructor;
import org.kaorun.financetracker.model.RoleEnum;
import org.kaorun.financetracker.model.UserModel;
import org.kaorun.financetracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь '" + username + "' не найден"));

        Collection<? extends GrantedAuthority> authorities = user.getRoles();
        if (authorities == null || authorities.isEmpty()) {
            authorities = Set.of(RoleEnum.USER);
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isActive())
                .authorities(authorities)
                .build();
    }
}