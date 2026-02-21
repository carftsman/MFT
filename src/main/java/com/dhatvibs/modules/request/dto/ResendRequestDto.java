package com.dhatvibs.modules.request.dto;

import lombok.Data;

@Data
public class ResendRequestDto {

    private Long formId;
    private String reason;
}