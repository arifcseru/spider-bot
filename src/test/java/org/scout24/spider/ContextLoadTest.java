package org.scout24.spider;



import static org.assertj.core.api.Assertions.assertThat;

import org.cky.spider.controller.CrawlerController;
import org.cky.spider.controller.HomeController;
import org.cky.spider.exceptions.GlobalExceptionHandler;
import org.cky.spider.repository.DocumentDetailsRepository;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.cky.spider.scheduler.MemoryCleanupTask;
import org.cky.spider.service.CrawlerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContextLoadTest {

	
	@Autowired
    private HomeController homeController;

	@Autowired
    private CrawlerController crawlerController;

	@Autowired
    private CrawlerService crawlerService;

	@Autowired
    private DocumentDetailsRepository documentDetailsRepository;
	
	@Autowired
    private HyperMediaLinkRepository hyperMediaLinkRepository;

	@Autowired
    private MemoryCleanupTask memoryCleanupTask;
	
	@Test
    public void contexLoadsHomeController() throws Exception {
        assertThat(homeController).isNotNull();
    }
	
	@Test
    public void contexLoadsCrawlerController() throws Exception {
        assertThat(crawlerController).isNotNull();
    }
	
	@Test
    public void contexLoadsCrawlerService() throws Exception {
        assertThat(crawlerService).isNotNull();
    }
	@Test
    public void contexLoadsDocumentDetailsRepository() throws Exception {
        assertThat(documentDetailsRepository).isNotNull();
    }

	@Test
    public void contexLoadsHyperMediaLinkRepository() throws Exception {
        assertThat(hyperMediaLinkRepository).isNotNull();
    }

	@Test
    public void contexLoadsMemoryCleanupTask() throws Exception {
        assertThat(memoryCleanupTask).isNotNull();
    }
	

}
