package org.scout24.spider;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.cky.spider.controller.CrawlerController;
import org.cky.spider.model.DocumentDetails;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.cky.spider.service.CrawlerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(CrawlerController.class)
public class CrawlerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CrawlerService crawlerService;

	@MockBean
	HyperMediaLinkRepository hyperMediaLinkRepository;

	@Test
	public void test_fecthDetails() throws Exception {
		given(this.crawlerService.fectchPageDetails("url"))
		.willReturn(new DocumentDetails());

		this.mockMvc.perform(post("/search").accept(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk());
	}

	@Test
	public void test_checkIfCompleted() throws Exception {
		given(this.hyperMediaLinkRepository.findByDocIdAndStatus(1l,-1))
		.willReturn(null);
		
		this.mockMvc.perform(get("/checkIfCompleted").accept(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk());
	}

	@Test
	public void test_refreshLinks() throws Exception {
		given(this.hyperMediaLinkRepository.findByDocId(1l))
		.willReturn(null);

		this.mockMvc.perform(post("/refreshLinks").accept(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk());
	}

}
