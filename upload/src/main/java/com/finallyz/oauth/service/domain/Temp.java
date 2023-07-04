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
@Table(name = "temp")
public class Temp {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "temp_vig_seq")
	@SequenceGenerator(name = "temp_vig_seq", allocationSize = 1, sequenceName = "temp_vig_seq")
	private Long id;
}
