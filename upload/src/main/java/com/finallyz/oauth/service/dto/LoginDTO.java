package com.finallyz.oauth.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	private String userName;
	private String mobileNumber;
	private String password;
	private Long farmerId;
	private String farmerName;
}
