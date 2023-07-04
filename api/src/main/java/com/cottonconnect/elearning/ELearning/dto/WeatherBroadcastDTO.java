package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherBroadcastDTO {
       
	private String redirectUrl;
    
	private Long country;
    
	private Long state;

    private Long district;

    private Long taluk;

    private Long village;


}
