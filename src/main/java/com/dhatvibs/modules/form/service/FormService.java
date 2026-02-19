package com.dhatvibs.modules.form.service;

import java.util.List;

import com.dhatvibs.modules.form.dto.FormRequestDto;
import com.dhatvibs.modules.form.dto.FormResponseDto;

import jakarta.servlet.http.HttpSession;

public interface FormService {

    FormResponseDto createForm(FormRequestDto requestDto, HttpSession session);

    List<FormResponseDto> getAllForms();
}
