package com.cottonconnect.elearning.ELearning.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cottonconnect.elearning.ELearning.model.FaqQueryResponse;

@Repository
public interface FaqResponseRepo extends JpaRepository<FaqQueryResponse, Long>{
	@Query(value = "SELECT count(l.id) FROM faq_query l  WHERE  NOT EXISTS (SELECT  faq_query_id FROM faq_response WHERE  faq_query_id = l.id)", nativeQuery = true)
	public Long getPendingCount();
	
	@Query(value = "SELECT count(id),q.created_date FROM faq_query q where q.created_date BETWEEN :startDate AND :endDate group by q.created_date order by q.created_date asc",nativeQuery = true)
	public List<Object[]> getQueriesReceived(Date startDate, Date endDate);
	
	@Query(value = "SELECT count(r.id),q.created_date FROM faq_query q left join faq_response r on r.faq_query_id = q.id where q.created_date BETWEEN :startDate AND :endDate group by q.created_date order by q.created_date asc",nativeQuery = true)
	public List<Object[]> getQueriesResponded(Date startDate, Date endDate);
}
