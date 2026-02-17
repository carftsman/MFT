package com.dhatvibs.modules.auth.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhatvibs.modules.auth.dto.*;
import com.dhatvibs.modules.auth.entity.User;
import com.dhatvibs.modules.auth.repository.UserRepository;
import com.dhatvibs.modules.auth.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDto activateUser(ActivateRequestDto request) {

        User user = userRepository.findByUserCode(request.getUserCode())
                .orElseThrow(() -> new RuntimeException("Invalid User ID"));

        if (user.getIsActivated()) {
            throw new RuntimeException("User already activated");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActivated(true);

        userRepository.save(user);

        return new AuthResponseDto("Activation Successful",
                user.getUserCode(),
                user.getRole().name());
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByUserCode(request.getUserCode())
                .orElseThrow(() -> new RuntimeException("Invalid User ID"));

        if (!user.getIsActivated()) {
            throw new RuntimeException("User not activated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return new AuthResponseDto("Login Successful",
                user.getUserCode(),
                user.getRole().name());
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
