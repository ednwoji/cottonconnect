package com.cottonconnect.elearning.ELearning.model;

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
@Table(name = "unknown_device")
public class UnknownDevice extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unknown_device_seq")
	@SequenceGenerator(name = "unknown_device_seq", allocationSize = 1, sequenceName = "unknown_device_seq")
	private Long id;
	
	private String serialNo;
	
	private String userName;
	
	private String mobileNo;
	
}
