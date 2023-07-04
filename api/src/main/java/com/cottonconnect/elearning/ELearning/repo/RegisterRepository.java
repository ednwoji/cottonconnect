package com.cottonconnect.elearning.ELearning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.Register;

@Repository
public interface RegisterRepository extends JpaRepository<Register, Long> {
	Register findByMobileNumberAndIsApprovedTrue(String mobileNumber);

	Register findByMobileNumber(String mobileNumber);
	
	@Query(value="SELECT count(t.id) FROM public.register t where t.is_approved is false", nativeQuery = true)
	Long getWaitingUsersCount();
	
	@Query(value="SELECT count(t.id) FROM public.register t where t.is_approved is true", nativeQuery = true)
	Long getApprovedUsersCount();
}
