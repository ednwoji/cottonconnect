package com.finallyz.oauth.service.domain;

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
@Table(name = "season")
public class Season extends AuditableBase{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_seq")
	@SequenceGenerator(name = "season_seq", allocationSize = 1, sequenceName = "season_seq")
	private Long id;

	private String name;

}
