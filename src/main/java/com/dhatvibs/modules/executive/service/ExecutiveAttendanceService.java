package com.dhatvibs.modules.executive.service;

import com.dhatvibs.modules.executive.dto.ExecutiveAttendanceRequestDto;

public interface ExecutiveAttendanceService {

    String markAttendance(ExecutiveAttendanceRequestDto dto);

    boolean isAttendanceMarkedToday();
}