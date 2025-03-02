package com.finance.auth.services;

import com.finance.auth.entities.RoleEntity;
import com.finance.auth.entities.UserEntity;
import com.finance.auth.models.LoginRequest;
import com.finance.auth.models.RegisterRequest;
import com.finance.auth.models.Role;
import com.finance.auth.repositories.RoleRepository;
import com.finance.auth.repositories.UserRepository;
import com.finance.auth.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity role = roleRepository.findByName(Role.USER)
                .orElseThrow(() -> new RuntimeException("Роль USER не найдена!"));

        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        UUID userId = user.getId();
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        return jwtService.generateToken(userDetails, userId);
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserEntity user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        UUID userId = user.getId();
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        return jwtService.generateToken(userDetails, userId);
    }
}
