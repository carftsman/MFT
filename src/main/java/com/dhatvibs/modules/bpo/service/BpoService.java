/*
 * package com.dhatvibs.modules.bpo.service;
 * 
 * import com.dhatvibs.modules.bpo.dto.BpoSubmitRequestDto;
 * 
 * public interface BpoService {
 * 
 * String submitForm(Long formId, BpoSubmitRequestDto request); }
 */ 

package com.dhatvibs.modules.bpo.service;

import java.util.List;

import com.dhatvibs.modules.bpo.dto.BpoSubmitRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

public interface BpoService {

    List<FormResponseDto> getFormsForBpo(HttpSession session);

    void submitForm(Long formId, BpoSubmitRequestDto dto, HttpSession session);
}
