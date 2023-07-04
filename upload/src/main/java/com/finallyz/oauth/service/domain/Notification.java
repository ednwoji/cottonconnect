package com.finallyz.oauth.service.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notify_seq")
	@SequenceGenerator(name = "notify_seq", allocationSize = 1, sequenceName = "notify_seq")
	private Long id;

	private String msg;

	private String description;

	@Transient
	private String redirectUrl;

	@ManyToMany
	@JoinTable(name = "notification_farmer", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farmer_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Farmer> farmers;

	@ManyToMany
	@JoinTable(name = "notification_country_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Country> countries;

	@ManyToMany
	@JoinTable(name = "notification_brand_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Brand> brands;

	@ManyToMany
	@JoinTable(name = "notification_programme_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "programme_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "notification_farm_group_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farm_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<FarmGroup> farmGroups;

	@ManyToMany
	@JoinTable(name = "notification_learner_group_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "learner_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<LearnerGroup> learnerGroups;

	@ManyToMany
	@JoinTable(name = "notification_user_map", joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<User> users;
}
