package com.dhatvibs.modules.form.dto;

import com.dhatvibs.modules.form.entity.FormStatus;
import lombok.Data;

@Data
public class FormRequestDto {

    private String vendorShopName;
    private String vendorName;
    private String contactNumber;
    private String mailId;
    private String doorNumber;
    private String streetName;
    private String areaName;
    private String pinCode;
    private String state;

    private FormStatus status;  // INTERESTED / NOT_INTERESTED
    
    private String review;

}
