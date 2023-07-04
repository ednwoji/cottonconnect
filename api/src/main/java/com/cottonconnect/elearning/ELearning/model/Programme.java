package com.cottonconnect.elearning.ELearning.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "programme")
public class Programme extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programme_seq")
	@SequenceGenerator(name = "programme_seq", allocationSize = 1, sequenceName = "programme_seq")
	private Long id;

	private String code;

	private String name;

}
