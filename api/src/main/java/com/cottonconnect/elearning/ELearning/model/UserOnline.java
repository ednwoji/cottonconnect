package com.cottonconnect.elearning.ELearning.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_online")
public class UserOnline {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
	@SequenceGenerator(name = "email_seq", allocationSize = 1, sequenceName = "email_seq")
	private Long id;
	
	@Column(name = "mobile_no", unique = true)
	private String mobileNo;
	
	private Date lastUpdatedDate;
}
