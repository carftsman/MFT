package com.dhatvibs.modules.teamlead.service;

import com.dhatvibs.modules.teamlead.dto.*;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import com.dhatvibs.modules.form.dto.*;

public interface TeamLeadService {

    ExecutiveResponseDto createExecutive(
            CreateExecutiveRequestDto request,
            String teamLeadCode);
    
    
    List<FormResponseDto> getMyExecutivesForms(HttpSession session);
}
