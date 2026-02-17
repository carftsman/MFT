package com.dhatvibs.modules.auth.dto;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String userCode;
    private String password;
}
