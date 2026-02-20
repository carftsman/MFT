package com.dhatvibs.modules.Data.dto;

import java.time.LocalDateTime;

import com.dhatvibs.modules.form.entity.FormStatus;
import com.dhatvibs.modules.form.entity.FormTag;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataResponseDto {

    private Long id;

    private Long executiveId;
    private String executiveName;

    private Long teamleadId;
    private String teamleadName;

    private String vendorShopName;
    private String vendorName;
    private String contactNumber;
    private String mailId;
    private String vendorLocation;

    private String doorNumber;
    private String streetName;
    private String areaName;
    private String pinCode;
    private String state;

    private FormTag tag;
    private FormStatus status;

    private String review;

    private Long assignedBpoId;
    private String assignedBpoName;

    private Boolean solved;
    private String executiveReview;
    private String vendorReview;

    private LocalDateTime bpoActionDate;
    private LocalDateTime reappearDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}