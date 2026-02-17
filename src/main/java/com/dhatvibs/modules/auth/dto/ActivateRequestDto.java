package com.dhatvibs.modules.auth.dto;

import lombok.Data;

@Data
public class ActivateRequestDto {

    private String userCode;
    private String password;
    private String confirmPassword;
}
