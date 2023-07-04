package com.cottonconnect.elearning.ELearning.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "weather_broadcast")
public class WeatherBroadcast {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_broadcast_id_seq")
	@SequenceGenerator(name = "weather_broadcast_id_seq", allocationSize = 1, sequenceName = "weather_broadcast_id_seq")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "state_id", nullable = false)
	private State state;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "district_id", nullable = false)
	private District district;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "taluk_id", nullable = false)
	private Taluk taluk;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "village_id", nullable = false)
	private Village village;

	private Boolean isActive;

	private Date createAt;

	private Boolean isDeleted;

	private String videoUrl;

	private String audioUrl;

	private String documentUrl;

}
