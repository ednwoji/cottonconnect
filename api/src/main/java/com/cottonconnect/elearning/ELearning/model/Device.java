package com.cottonconnect.elearning.ELearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "device")
public class Device extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_seq")
	@SequenceGenerator(name = "device_seq", allocationSize = 1, sequenceName = "device_seq")
	private Long id;

	private String serialNo;

	private String mobileModel;

	@Column(name="imei", unique=true)
	private String imei;

	private String osVersion;

	private String manufacturer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "farmer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Farmer farmer;
	
	@Transient
	private String mobileNo;
}
