package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Column;
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
@Table(name = "faq_query")
public class FaqQuery extends AuditableBase{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_seq")
	@SequenceGenerator(name = "faq_seq", allocationSize = 1, sequenceName = "faq_seq")
	private Long id;

	@OneToMany(mappedBy = "faqQuery")
	@JsonIgnore
	private List<FaqDocuments> faqImages;
	
	@Column(columnDefinition = "TEXT")
	private String query;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "farmer_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Farmer farmer;
	
	private String imei;
	
	@OneToMany(mappedBy = "faqQuery")
	@JsonIgnore
	private List<FaqQueryResponse> responseList;

}
