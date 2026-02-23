package com.dhatvibs.modules.bporequest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.bporequest.dto.BpoModifyDto;
import com.dhatvibs.modules.bporequest.dto.CorrectionRequestDto;
import com.dhatvibs.modules.bporequest.dto.ManagerApprovalDto;
import com.dhatvibs.modules.bporequest.service.BpoRequestService;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/bpo-request")
public class BpoRequestController {

    @Autowired
    private BpoRequestService service;

    @GetMapping("/history")
    public List<FormResponseDto> getHistory(HttpSession session) {
        return service.getBpoHistory(session);
    }

    @PutMapping("/request/{formId}")
    public FormResponseDto requestCorrection(
            @PathVariable Long formId,
            @RequestBody CorrectionRequestDto dto,
            HttpSession session) {

        return service.requestCorrection(formId, dto, session);
    }

    @GetMapping("/manager/requests")
    public List<FormResponseDto> getManagerRequests(HttpSession session) {
        return service.getManagerCorrectionRequests(session);
    }

    @PutMapping("/manager/approve/{formId}")
    public FormResponseDto approve(
            @PathVariable Long formId,
            @RequestBody ManagerApprovalDto dto,
            HttpSession session) {

        return service.approveCorrection(formId, dto, session);
    }

    @PutMapping("/resubmit/{formId}")
    public FormResponseDto resubmit(
            @PathVariable Long formId,
            @RequestBody BpoModifyDto dto,
            HttpSession session) {

        return service.modifyAndResubmit(formId, dto, session);
    }
}