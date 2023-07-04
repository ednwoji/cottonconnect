package com.cottonconnect.elearning.ELearning.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadLgUploadExcelDTO {

    private String sNo;
    private String country;
    private String brand;
    private String programme;

    private String localPartner;
    private String farmGroup;
    private String name;
    private Long noOfFarmers;
    private Long acreage;
    private Long yield;

}
