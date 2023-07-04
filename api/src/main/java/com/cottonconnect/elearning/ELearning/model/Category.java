package com.cottonconnect.elearning.ELearning.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	@SequenceGenerator(name = "category_seq", allocationSize = 1, sequenceName = "category_seq")
	private Long id;

	private String name;

	@OneToMany(mappedBy = "category")
	private List<SubCategory> subCategories;
}
