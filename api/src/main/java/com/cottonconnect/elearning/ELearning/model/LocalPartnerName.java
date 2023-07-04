package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "local_partner")
public class LocalPartnerName extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lpn_seq")
	@SequenceGenerator(name = "lpn_seq", allocationSize = 1, sequenceName = "lpn_seq")
	private Long id;

	private String name;

	private String address;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "country_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	private boolean status;

	@ManyToMany
	@JoinTable(name = "local_partner_programs", joinColumns = @JoinColumn(name = "partner_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "program_id", referencedColumnName = "id"))
	private List<Programme> Programs;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Brand brand;
	
}
