package com.cottonconnect.elearning.ELearning.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "farm_group")
public class FarmGroup extends AuditableBase {
	enum Level {
		PRODUCER_UNIT, FARM_GROUP, ORGANIC_FARM_GROUP
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fg_seq")
	@SequenceGenerator(name = "fg_seq", allocationSize = 1, sequenceName = "fg_seq")
	private Long id;

	private Long typez;

	private String name;

	private String latitude;

	private String longitude;

	private Long noOfFarmers;

	private Long acreage;

	private Long estYield;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "country_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "local_partner_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private LocalPartnerName localPartner;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Brand brand;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "program_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Programme program;
}
