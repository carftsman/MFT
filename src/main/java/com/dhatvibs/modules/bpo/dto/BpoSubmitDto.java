package com.dhatvibs.modules.bpo.dto;

import lombok.Data;

@Data
public class BpoSubmitDto {

    private String action;  // SOLVED or NOT_SOLVED
    // private String review; 
    
    //added
    private String idNumber;     //added
    private String bpoName;   //added
    private String executiveReview;
    private String vendorReview;

}
