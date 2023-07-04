package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	@Query(value = "From Notification n join n.farmGroups fg where fg.id=:farmGroupId order by n.updatedDate desc")	
	List<Notification> listNotificationByFamGroup( Long farmGroupId);
}
