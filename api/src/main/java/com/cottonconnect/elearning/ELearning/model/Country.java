package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "country")
public class Country extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_seq")
	@SequenceGenerator(name = "country_seq", allocationSize = 1, sequenceName = "country_seq")
	private Long id;

	private String code;

	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "country")
	private List<State> states;
}
