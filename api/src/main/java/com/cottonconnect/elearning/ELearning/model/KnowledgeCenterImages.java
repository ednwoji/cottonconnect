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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "eknowledge_center_images")
@JsonInclude(Include.NON_NULL)
public class KnowledgeCenterImages{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kc_img_seq")
	@SequenceGenerator(name = "kc_img_seq", allocationSize = 1, sequenceName = "kc_img_seq")
	private Long id;
	
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "knowledge_center_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private KnowledgeCenter knowledgeCenter;
}
