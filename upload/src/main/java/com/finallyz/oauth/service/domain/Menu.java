package com.finallyz.oauth.service.domain;

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
@Table(name = "cc_menu")
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_menu_seq")
	@SequenceGenerator(name = "cc_menu_seq", allocationSize = 1, sequenceName = "cc_menu_seq")
	private Long id;

	private String name;

	private String icon;

	private Integer seq;

	private String dispName;

	private Long parentId;

	private String url;

	@OneToMany(mappedBy = "menu")
	private List<Entitlements> entitlements;

}
