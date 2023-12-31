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
@Table(name = "faq_documents")
public class FaqDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_docs_seq")
	@SequenceGenerator(name = "faq_docs_seq", allocationSize = 1, sequenceName = "faq_docs_seq")
	private Long id;

	private String url;

	private String docType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "faq_query_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private FaqQuery faqQuery;
}
