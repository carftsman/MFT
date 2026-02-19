package com.dhatvibs.modules.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.auth.dto.*;
import com.dhatvibs.modules.auth.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

	/*
	 * @PostMapping("/activate") public AuthResponseDto activate(
	 * 
	 * @RequestBody ActivateRequestDto request, HttpSession session) {
	 * 
	 * AuthResponseDto response = authService.activateUser(request);
	 * 
	 * session.setAttribute("userCode", response.getUserCode());
	 * 
	 * return response; }
	 */
    
    @PostMapping("/activate")
    public AuthResponseDto activate(
            @RequestBody ActivateRequestDto request,
            HttpSession session) {

        return authService.activateUser(request, session);
    }

    
    
    
	/*
	 * @PostMapping("/login") public AuthResponseDto login(
	 * 
	 * @RequestBody LoginRequestDto request, HttpSession session) {
	 * 
	 * AuthResponseDto response = authService.login(request);
	 * 
	 * session.setAttribute("userCode", response.getUserCode());
	 * 
	 * return response; }
	 */
    
    @PostMapping("/login")
    public AuthResponseDto login(
            @RequestBody LoginRequestDto request,
            HttpSession session) {

        return authService.login(request, session);
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "Logged Out Successfully";
    }
}
