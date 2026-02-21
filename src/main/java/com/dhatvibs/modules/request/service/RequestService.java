package com.dhatvibs.modules.request.service;

import java.util.List;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;
import com.dhatvibs.modules.request.dto.ResendRequestDto;

import jakarta.servlet.http.HttpSession;

public interface RequestService {

    void requestResend(ResendRequestDto dto, HttpSession session);

    List<FormResponseDto> getManagerResendRequests(HttpSession session);

    void approveResend(Long formId, HttpSession session);

    void rejectResend(Long formId, HttpSession session);

    List<FormResponseDto> getApprovedResendForms(HttpSession session);

    FormResponseDto resubmitForm(Long formId,
                                 FormRequestDto dto,
                                 HttpSession session);
}