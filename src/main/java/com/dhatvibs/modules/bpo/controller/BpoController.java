package com.dhatvibs.modules.bpo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.bpo.dto.BpoSubmitRequestDto;
import com.dhatvibs.modules.bpo.service.BpoService;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bpo")
public class BpoController {

    @Autowired
    private BpoService bpoService;

    @GetMapping("/forms")
    public List<FormResponseDto> getForms(HttpSession session) {
        return bpoService.getFormsForBpo(session);
    }

    @PostMapping("/submit/{formId}")
    public String submitForm(@PathVariable Long formId,
                             @RequestBody BpoSubmitRequestDto dto,
                             HttpSession session) {

        bpoService.submitForm(formId, dto, session);
        return "Form submitted successfully";
    }
}
