package org.cky.spider.scheduler;

import java.util.Calendar;

import org.cky.spider.repository.DocumentDetailsRepository;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author chandrakumar
 *
 */
@Component
public class MemoryCleanupTask {
	private static final Logger log = LoggerFactory.getLogger(MemoryCleanupTask.class);

	DocumentDetailsRepository documentDetailsRepository;
	HyperMediaLinkRepository hyperMediaLinkRepository;

    public MemoryCleanupTask(DocumentDetailsRepository documentDetailsRepository,
			HyperMediaLinkRepository hyperMediaLinkRepository) {
		super();
		this.documentDetailsRepository = documentDetailsRepository;
		this.hyperMediaLinkRepository = hyperMediaLinkRepository;
	}

	@Scheduled(initialDelay =10*60*1000,fixedRate = 20*60*1000)
    public void clearOldRecord() {
    		System.out.println("Current time is :: " + Calendar.getInstance().getTime());
    		
    		 Long docId= documentDetailsRepository.getMaxId();
    		 
    		 if(docId>1) {
    			 documentDetailsRepository.deleteOldRecords(docId);
    			 hyperMediaLinkRepository.deleteOldRecords(docId);
    		 }
    }
}
