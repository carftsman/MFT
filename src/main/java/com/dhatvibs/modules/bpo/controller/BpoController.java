package com.dhatvibs.modules.bpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.bpo.dto.BpoSubmitDto;
import com.dhatvibs.modules.bpo.service.BpoService;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bpo")
public class BpoController {

    @Autowired
    private BpoService bpoService;

    // GET Dashboard Forms
    @GetMapping("/forms")
    public List<FormResponseDto> getDashboardForms(HttpSession session) {
        return bpoService.getDashboardForms(session);
    }

    // POST Submit Form
    @PostMapping("/submit/{formId}")
    public FormResponseDto submitForm(
            @PathVariable Long formId,
            @RequestBody BpoSubmitDto dto,
            HttpSession session) {

        return bpoService.submitForm(formId, dto, session);
    }
}
