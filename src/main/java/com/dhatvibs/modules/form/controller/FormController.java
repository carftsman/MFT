package com.dhatvibs.modules.form.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.form.service.FormService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/form")
public class FormController {

    @Autowired
    private FormService formService;

    // POST - Create Form
    @PostMapping
    public FormResponseDto createForm(
            @RequestBody FormRequestDto requestDto,
            HttpSession session) {

        return formService.createForm(requestDto, session);
    }

    // GET - Get All Forms
    @GetMapping
    public List<FormResponseDto> getAllForms() {
        return formService.getAllForms();
    }   
    
    @GetMapping("/my-history")
    public ResponseEntity<List<FormResponseDto>> getMyHistory(HttpSession session) {
        return ResponseEntity.ok(formService.getMyForms(session));
    }
    
    
    @GetMapping("/executive-forms")
    public ResponseEntity<List<FormResponseDto>> getExecutiveForms(
            HttpSession session) {

        String role = (String) session.getAttribute("role");

        if (role == null ||
            (!role.equalsIgnoreCase("MANAGER")
             && !role.equalsIgnoreCase("REPORTER"))) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(
                formService.getExecutiveForms());
    }
    
    
} 
