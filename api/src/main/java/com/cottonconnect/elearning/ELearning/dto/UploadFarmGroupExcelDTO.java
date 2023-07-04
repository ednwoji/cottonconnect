package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFarmGroupExcelDTO {

    private String name;
    private Long type;
    private String latitude;
    private String longitude;
    private Long noOfFarmers;
    private Long acreage;
    private Long yield;

}
