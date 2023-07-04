package com.cottonconnect.elearning.ELearning.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AuditableBase {

	enum Level {
		WEB_USER, BRAND_USER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1, sequenceName = "user_seq")
	private Long id;

	@Column(name = "user_id", unique = true)
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

	private Long userType;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

}
