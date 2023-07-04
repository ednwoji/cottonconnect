package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFarmerDTO {
    
	private String redirectUrl;
    
	private Long country;

    private Long state;

    private Long district;

    private Long taluk;

    private Long village;

    private Long brand;

    private Long program;

    private Long localPartner;

    private Long farmGroup;

    private Long learnerGroup;
}
