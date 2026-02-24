package com.dhatvibs.modules.form.dto;

import com.dhatvibs.modules.form.entity.FormStatus;
import com.dhatvibs.modules.form.entity.VendorType;

import lombok.Data;

@Data
public class FormRequestDto {

    private String vendorShopName;
    private String vendorName;
    private String contactNumber;
    private String mailId;
    private VendorType vendorType;  //added
    private String vendorLocation;  //added
    private Double latitude;        //added
    private Double longitude;       //added
    private String doorNumber;
    private String streetName;
    private String areaName;
    private String pinCode;
    private String district;    //added
    private String state;

    private FormStatus status;  // INTERESTED / NOT_INTERESTED
    
    private String review;

}
