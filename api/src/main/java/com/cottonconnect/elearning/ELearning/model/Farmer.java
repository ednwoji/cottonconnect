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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "e_cotton_farmer")
public class Farmer extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e_farmer_seq")
	@SequenceGenerator(name = "e_farmer_seq", allocationSize = 1, sequenceName = "e_farmer_seq")
	private Long id;

	private String name;

	@Column(unique = true)
	private String mobileNumber;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "programme_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Programme programme;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "country_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "state_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private State state;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "district_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private District district;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "taluk_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Taluk taluk;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "village_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Village village;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "farm_group_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FarmGroup farmGroup;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "learner_group_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private LearnerGroup learnerGroup;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "brand_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Brand brand;

	private String fieldExecutive;

	private String farmerCode;

	private Long typez;

	private String countryCode;

	private String puCode;
}
