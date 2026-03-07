package com.dhatvibs.modules.auth.dto;

import lombok.Data;

@Data
public class ResetPasswordRequestDto {

    private String token;
    private String newPassword;
    private String confirmPassword;
}