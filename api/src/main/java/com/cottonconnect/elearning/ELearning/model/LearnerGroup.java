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
@Table(name = "learner_group")
public class LearnerGroup extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lg_seq")
	@SequenceGenerator(name = "lg_seq", allocationSize = 1, sequenceName = "lg_seq")
	private Long id;

	private String name;

	private String latitude;

	private String longitude;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "farm_group_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FarmGroup farmGroup;

	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "programme_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Programme programme;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Brand brand;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "country_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	private Long noOfFarmers;

	private Long acreage;

	private Long estYield;
}
