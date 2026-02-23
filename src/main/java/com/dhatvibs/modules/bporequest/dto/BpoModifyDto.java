package com.dhatvibs.modules.bporequest.dto;

import lombok.Data;

@Data
public class BpoModifyDto {

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

    private String executiveReview;
    private String vendorReview;
}