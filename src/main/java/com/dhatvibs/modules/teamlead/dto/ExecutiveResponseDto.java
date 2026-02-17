package com.dhatvibs.modules.teamlead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutiveResponseDto {

    private String executiveCode;
    private String name;
    private String phone;
    private String message;
}
