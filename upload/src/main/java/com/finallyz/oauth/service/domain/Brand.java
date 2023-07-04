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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "brand")
public class Brand extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq")
	@SequenceGenerator(name = "brand_seq", allocationSize = 1, sequenceName = "brand_seq")
	private Long id;

	private String code;

	private String name;


	private String contactPersonName;

	private String contactNo;

	private String mobileNo;

	private String landLineNo;

	private String email;

	@ManyToMany
	@JoinTable(name = "brand_users", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<User> users;

	@ManyToMany
	@JoinTable(name = "brand_programs", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "brand_countries", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Country> countries;
}
