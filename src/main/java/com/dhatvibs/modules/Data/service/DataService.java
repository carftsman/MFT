package com.dhatvibs.modules.Data.service;

import java.util.List;

import com.dhatvibs.modules.Data.dto.DataResponseDto;

public interface DataService {

    List<DataResponseDto> getAllFormData();
}