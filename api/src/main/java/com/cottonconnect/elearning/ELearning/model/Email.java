package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "email")
public class Email extends AuditableBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")
	@SequenceGenerator(name = "email_seq", allocationSize = 1, sequenceName = "email_seq")
	private Long id;

	@ManyToMany
	@JoinTable(name = "email_programme_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "programme_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<Programme> programmes;

	@ManyToMany
	@JoinTable(name = "email_farm_group_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "farm_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<FarmGroup> farmGroups;

	@ManyToMany
	@JoinTable(name = "email_learner_group_map", joinColumns = @JoinColumn(name = "eknowledge_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "learner_group_id", referencedColumnName = "id"))
	@JsonIgnore
	private List<LearnerGroup> learnerGroups;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "country_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Brand brand;

	@OneToMany(mappedBy = "emailId")
	private List<EmailList> emailLists;

	@Transient
	private String emailings;
}
