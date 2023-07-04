package com.cottonconnect.elearning.ELearning.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@FilterDef(name = "filterByDeletedFlag", parameters = { @ParamDef(name = "isDeleted", type = "boolean") })
@Filters({ @Filter(name = "filterByDeletedFlag", condition = "is_deleted = :isDeleted") })
public class AuditableBase {
	@CreatedDate
	private @NonNull Date createdDate;
	private @NonNull Date updatedDate;
	private @NonNull String createdUser;
	@LastModifiedDate
	private @NonNull String updatedUser;
	private boolean isActive;
	private boolean isDeleted;
}
