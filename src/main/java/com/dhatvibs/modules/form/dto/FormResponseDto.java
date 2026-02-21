package com.dhatvibs.modules.form.dto;

import com.dhatvibs.modules.form.entity.FormStatus;
import com.dhatvibs.modules.form.entity.FormTag;
import com.dhatvibs.modules.form.entity.VendorType;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FormResponseDto {

    private Long id;
    private Long executiveId;
    private String executiveName;  //added
    private Long teamleadId;
    private String teamleadName;   //added

    private String vendorShopName;
    private String vendorName;
    private String contactNumber;
    private String mailId;
    private VendorType vendorType;  //added
    private String vendorLocation;  //added
    private Double latitude;
    private Double longitude;
    private String doorNumber;  //added
    private String streetName;  //added
    private String areaName;   
    private String pinCode;     //added
    private String state;

    private FormTag tag;
    private FormStatus status;
    private String review;


    private LocalDateTime createdAt;
}
