package com.finallyz.oauth.service.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "register")
public class Register {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_seq")
	@SequenceGenerator(name = "register_seq", allocationSize = 1, sequenceName = "register_seq")
	public Long id;

	public String name;
	public String mobileNumber;
	public String countryName;
	@Column(name = "imei", unique = true)
	public String imei;
	public String lat;
	public String lon;
	private String manufacturer;
	private boolean isApproved;
	private String organization;
	
	@ManyToMany
	@JoinTable(name = "register_country_map", joinColumns = @JoinColumn(name = "register_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Country> countries;
	
	@ManyToMany
	@JoinTable(name = "register_brand_map", joinColumns = @JoinColumn(name = "register_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Brand> brands;

	@ManyToMany
	@JoinTable(name = "register_programme_map", joinColumns = @JoinColumn(name = "register_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "programme_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "register_farm_group_map", joinColumns = @JoinColumn(name = "register_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farm_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<FarmGroup> farmGroups;

	@ManyToMany
	@JoinTable(name = "register_learner_group_map", joinColumns = @JoinColumn(name = "register_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "learner_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<LearnerGroup> learnerGroups;

}
