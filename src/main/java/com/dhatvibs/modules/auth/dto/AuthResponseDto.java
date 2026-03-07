package com.dhatvibs.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {

    private String message;
    private String userCode;
    private String role;
    private String name; //added
}
