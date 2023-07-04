package com.cottonconnect.elearning.ELearning.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	private Long id;
	
	private String userId;

	private String name;

	private String mobileNo;

	private String emailId;

	private Integer accessLevel;

	private Long deviceId;

	private boolean status;

	private Long programmeId;

	private Long lpnId;

	private Long icsId;

	private Long puId;

	private Long villageId;

	private String password;
	
	private Long roleId;

}
