package com.dhatvibs.modules.bpo.service;

import java.util.List;

import com.dhatvibs.modules.bpo.dto.BpoSubmitDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

public interface BpoService {

    List<FormResponseDto> getDashboardForms(HttpSession session);

    FormResponseDto submitForm(Long formId, BpoSubmitDto dto, HttpSession session); 
    
    //added
    List<FormResponseDto> getReappearForms(HttpSession session); 
    
    //added for executive get bpo solved forms.
     List<FormResponseDto> getSolvedForms(HttpSession session);
}
