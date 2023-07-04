package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EnableJpaAuditing
@Table(name = "eknowledge_center")
public class KnowledgeCenter extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "e_knowledge_seq")
	@SequenceGenerator(name = "e_knowledge_seq", allocationSize = 1, sequenceName = "e_knowledge_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sub_category_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private SubCategory subCategory;

	private String title;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String identification;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(columnDefinition = "TEXT")
	private String notes;
	
	@Column(columnDefinition = "TEXT")
	private String externalLink;

	@OneToMany(mappedBy = "knowledgeCenter")
	@JsonIgnore
	private List<KnowledgeCenterImages> knowledgeCenterImages;

	private Long typez;
	
	@ManyToMany
	@JoinTable(name = "eknowledge_country_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Country> countries;
	
	@ManyToMany
	@JoinTable(name = "eknowledge_brand_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Brand> brands;

	@ManyToMany
	@JoinTable(name = "eknowledge_programme_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "programme_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "eknowledge_farm_group_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farm_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<FarmGroup> farmGroups;

	@ManyToMany
	@JoinTable(name = "eknowledge_learner_group_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "learner_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<LearnerGroup> learnerGroups;

	private Long type;

}
