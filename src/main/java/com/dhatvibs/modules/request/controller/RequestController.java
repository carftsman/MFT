package com.dhatvibs.modules.request.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.request.dto.ResendRequestDto;
import com.dhatvibs.modules.request.service.RequestService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // 2️⃣ Executive Request Resend
    @PostMapping("/resend")
    public void requestResend(@RequestBody ResendRequestDto dto,
                              HttpSession session) {
        requestService.requestResend(dto, session);
    }

    // 5️⃣ Manager View
    @GetMapping("/manager")
    public List<FormResponseDto> getManagerRequests(HttpSession session) {
        return requestService.getManagerResendRequests(session);
    }

    // 6️⃣ Approve
    @PutMapping("/{formId}/approve")
    public void approve(@PathVariable Long formId,
                        HttpSession session) {
        requestService.approveResend(formId, session);
    }

    // 7️⃣ Reject
    @PutMapping("/{formId}/reject")
    public void reject(@PathVariable Long formId,
                       HttpSession session) {
        requestService.rejectResend(formId, session);
    }

    // 3️⃣ Executive Approved Forms
    @GetMapping("/approved")
    public List<FormResponseDto> getApproved(HttpSession session) {
        return requestService.getApprovedResendForms(session);
    }

    // 4️⃣ Resubmit
    @PutMapping("/{formId}/resubmit")
    public FormResponseDto resubmit(@PathVariable Long formId,
                                    @RequestBody FormRequestDto dto,
                                    HttpSession session) {
        return requestService.resubmitForm(formId, dto, session);
    }
}