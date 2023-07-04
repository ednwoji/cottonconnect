package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "video")
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_cc_seq")
	@SequenceGenerator(name = "video_cc_seq", allocationSize = 1, sequenceName = "video_cc_seq")
	private Long id;

	private String name;

	private String description;

	private boolean isRenderingCompleted;

	private String url;

	private String link;

	private boolean isRendered;

	@OneToMany(mappedBy = "video")
	@JsonIgnore
	private List<VideoDocuments> documents;

	@Transient
	private String redirectUrl;

	private String status;

	@ManyToMany
	@JoinTable(name = "video_country_map", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Country> countries;
	
	@ManyToMany
	@JoinTable(name = "video_brand_map", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Brand> brands;

	@ManyToMany
	@JoinTable(name = "video_programme_map", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "programme_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "video_farm_group_map", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farm_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<FarmGroup> farmGroups;

	@ManyToMany
	@JoinTable(name = "video_learner_group_map", joinColumns = @JoinColumn(name = "video_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "learner_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<LearnerGroup> learnerGroups;

	@Transient
	private List<Long> programs;
	@Transient
	private List<Long> localPartners;
	@Transient
	private List<Long> farmGroup;
	@Transient
	private List<Long> learners;
	
	private String sourceUrl;

	private Long type;
}
