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

	/*
	 * @Override public AuthResponseDto activateUser(ActivateRequestDto request,HttpSession session) {
	 * 
	 * User user = userRepository.findByUserCode(request.getUserCode())
	 * .orElseThrow(() -> new RuntimeException("Invalid User ID"));
	 * 
	 * if (user.getIsActivated()) { throw new
	 * RuntimeException("User already activated"); }
	 * 
	 * if (!request.getPassword().equals(request.getConfirmPassword())) { throw new
	 * RuntimeException("Passwords do not match"); }
	 * 
	 * user.setPassword(passwordEncoder.encode(request.getPassword()));
	 * user.setIsActivated(true);
	 * 
	 * userRepository.save(user);
	 * 
	 * return new AuthResponseDto("Activation Successful", user.getUserCode(),
	 * user.getRole().name()); }
	 */
    
    @Override
    public AuthResponseDto activateUser(ActivateRequestDto request, HttpSession session) {

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

        // ✅ CREATE SESSION AFTER ACTIVATION
        session.setAttribute("userId", user.getId());
        session.setAttribute("userCode", user.getUserCode());
        session.setAttribute("role", user.getRole().name());
        session.setAttribute("executiveName", user.getName());

        // ✅ If BPO or EXECUTIVE linked to TeamLead
        if (user.getTeamleadId() != null) {

            User teamlead = userRepository.findById(user.getTeamleadId())
                    .orElseThrow(() -> new RuntimeException("Teamlead not found"));

            session.setAttribute("teamleadId", teamlead.getId());
            session.setAttribute("teamleadName", teamlead.getName());
        }

        return new AuthResponseDto(
                "Activation Successful",
                user.getUserCode(),
                user.getRole().name()
        );
    }


	/*
	 * @Override public AuthResponseDto login(LoginRequestDto request) {
	 * 
	 * User user = userRepository.findByUserCode(request.getUserCode())
	 * .orElseThrow(() -> new RuntimeException("Invalid User ID"));
	 * 
	 * if (!user.getIsActivated()) { throw new
	 * RuntimeException("User not activated"); }
	 * 
	 * if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	 * throw new RuntimeException("Invalid Password"); }
	 * 
	 * return new AuthResponseDto("Login Successful", user.getUserCode(),
	 * user.getRole().name()); }
	 */
	/*
	 * @Override public AuthResponseDto login(LoginRequestDto request, HttpSession
	 * session) {
	 * 
	 * User user = userRepository.findByUserCode(request.getUserCode())
	 * .orElseThrow(() -> new RuntimeException("Invalid User ID"));
	 * 
	 * if (!user.getIsActivated()) { throw new
	 * RuntimeException("User not activated"); }
	 * 
	 * if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	 * throw new RuntimeException("Invalid Password"); }
	 * 
	 * // ✅ CREATE SESSION & STORE VALUES session.setAttribute("userId",
	 * user.getId()); session.setAttribute("role", user.getRole().name());
	 * session.setAttribute("teamleadId", user.getTeamleadId());
	 * session.setAttribute("userCode", user.getUserCode());
	 * 
	 * 
	 * return new AuthResponseDto( "Login Successful", user.getUserCode(),
	 * user.getRole().name() ); }
	 */
    
    
    
    @Override
    public AuthResponseDto login(LoginRequestDto request, HttpSession session) {

        User user = userRepository.findByUserCode(request.getUserCode())
                .orElseThrow(() -> new RuntimeException("Invalid User ID"));

        if (!user.getIsActivated()) {
            throw new RuntimeException("User not activated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        // ✅ STORE BASIC DETAILS
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole().name());
        session.setAttribute("teamleadId", user.getTeamleadId());
        session.setAttribute("userCode", user.getUserCode());

        // ✅ STORE EXECUTIVE NAME
        session.setAttribute("executiveName", user.getName());

        // ✅ FETCH & STORE TEAMLEAD NAME
        if (user.getTeamleadId() != null) {

            User teamlead = userRepository.findById(user.getTeamleadId())
                    .orElseThrow(() -> new RuntimeException("Teamlead not found"));

            session.setAttribute("teamleadName", teamlead.getName());
        }

        return new AuthResponseDto(
                "Login Successful",
                user.getUserCode(),
                user.getRole().name()
        );
    }



    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
