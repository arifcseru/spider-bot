package org.cky.spider.repository;

import javax.transaction.Transactional;

import org.cky.spider.model.DocumentDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DocumentDetailsRepository extends CrudRepository<DocumentDetails, Long>{
	@Query("SELECT coalesce(max(dd.id), 0) FROM DocumentDetails dd")
	Long getMaxId();
	
	@Transactional
	@Modifying
	@Query(" delete from #{#entityName} dd where dd.id <>  :id")
	void deleteOldRecords(@Param("id")Long id);
}
