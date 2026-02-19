/*
 * package com.dhatvibs.modules.bpo.dto;
 * 
 * import lombok.Data;
 * 
 * @Data public class BpoSubmitRequestDto {
 * 
 * private Boolean solved; // true or false
 * 
 * private String executiveReview;
 * 
 * private String vendorReview; }
 */ 

package com.dhatvibs.modules.bpo.dto;

import lombok.Data;

@Data
public class BpoSubmitRequestDto {

    private Boolean solved;
    private String review;
}
