package com.dhatvibs.modules.teamlead.service;

import com.dhatvibs.modules.teamlead.dto.*;

public interface TeamLeadService {

    ExecutiveResponseDto createExecutive(
            CreateExecutiveRequestDto request,
            String teamLeadCode);
}
