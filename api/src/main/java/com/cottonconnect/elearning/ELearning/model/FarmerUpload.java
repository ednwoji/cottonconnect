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
@Table(name = "farmer_upload")
public class FarmerUpload extends AuditableBase{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "farmer_upload_seq")
	@SequenceGenerator(name = "farmer_upload_seq", allocationSize = 1, sequenceName = "farmer_upload_seq")
	private Long id;
	
	private Integer noOfFarmers;
	
	private Integer successCount;
	
	private Integer failureCount;
	
	@OneToMany(mappedBy = "farmerUpload")
	private List<FarmerUploadFailure> failureList;
}
