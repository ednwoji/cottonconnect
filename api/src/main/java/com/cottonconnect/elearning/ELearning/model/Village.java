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
@Table(name="village")
public class Village extends AuditableBase{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "village_seq")
	@SequenceGenerator(name = "village_seq", allocationSize = 1, sequenceName = "village_seq")
	private Long id;
	
	private String code;
	
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "taluk_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Taluk taluk;
}
