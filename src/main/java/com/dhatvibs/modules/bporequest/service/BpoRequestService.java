package com.dhatvibs.modules.bporequest.service;

import java.util.List;
import java.util.Map;

import com.dhatvibs.modules.bporequest.dto.BpoModifyDto;
import com.dhatvibs.modules.bporequest.dto.CorrectionRequestDto;
import com.dhatvibs.modules.bporequest.dto.ManagerApprovalDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

public interface BpoRequestService {

    List<FormResponseDto> getBpoHistory(HttpSession session);

    FormResponseDto requestCorrection(Long formId,
                                      CorrectionRequestDto dto,
                                      HttpSession session);

    List<FormResponseDto> getManagerCorrectionRequests(HttpSession session);

    FormResponseDto approveCorrection(Long formId,
                                      ManagerApprovalDto dto,
                                      HttpSession session);

    FormResponseDto modifyAndResubmit(Long formId,
                                      BpoModifyDto dto,
                                      HttpSession session); 
    
    Long getMyRequestCount(HttpSession session);

    Map<String, Long> getMyApprovalStats(HttpSession session);  
    
    List<FormResponseDto> getReopenedForms(HttpSession session);
}