package com.finallyz.oauth.service.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "faq")
public class FAQ extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_q_seq")
	@SequenceGenerator(name = "faq_q_seq", allocationSize = 1, sequenceName = "faq_q_seq")
	private Long id;

	private String question;

	private String answer;
	
	@Transient
	private String redirectUrl;

}
