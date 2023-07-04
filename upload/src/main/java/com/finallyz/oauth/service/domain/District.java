package com.finallyz.oauth.service.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name="district")
public class District extends AuditableBase{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "district_seq")
	@SequenceGenerator(name = "district_seq", allocationSize = 1, sequenceName = "district_seq")
	private Long id;
	
	private String code;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "state_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private State state;
	
	@OneToMany(mappedBy = "district")
	private List<Taluk> taluks;
}
