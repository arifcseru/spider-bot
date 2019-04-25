package org.cky.spider.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.cky.spider.model.HyperMediaLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface HyperMediaLinkRepository extends JpaRepository<HyperMediaLink, Long>{
	List<HyperMediaLink> findByDocId(Long docid);
	
	List<HyperMediaLink> findByDocIdAndStatus(Long docid,Integer status);
	
	@Transactional
	@Modifying
	@Query(" delete from #{#entityName} hml where hml.docId <> :docId")
	void deleteOldRecords(@Param("docId")Long docId);
}
