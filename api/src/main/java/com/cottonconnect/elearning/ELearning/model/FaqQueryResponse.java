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
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "faq_response")
public class FaqQueryResponse extends AuditableBase{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_response_seq")
	@SequenceGenerator(name = "faq_response_seq", allocationSize = 1, sequenceName = "faq_response_seq")
	private Long id;

	@Transient
	private Long queryId;
	
	private String query;
	
	public String url;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "faqQuery_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FaqQuery faqQuery;
	
	@Transient
	private String user;
	
}
