package com.finallyz.oauth.service.domain;

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
@Table(name = "video_documents")
public class VideoDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cc_video_seq")
	@SequenceGenerator(name = "cc_video_seq", allocationSize = 1, sequenceName = "cc_video_seq")
	private Long id;

	private String url;

	private String docType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "video_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Video video;
}
