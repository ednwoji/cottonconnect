package com.finallyz.oauth.service.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
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
