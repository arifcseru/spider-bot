package org.scout24.spider;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.cky.spider.model.HyperMediaLink;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class HyperMediaLinkRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private HyperMediaLinkRepository hmlRepository;

	@Test
	public void testSave() {

		HyperMediaLink hml=new HyperMediaLink();
		hml.setLinkAddress("www.testspider.com");
		hml.setLinkType("INTERNAL");
		
		HyperMediaLink hml_saved = hmlRepository.save(hml);

		assertThat(hml_saved).isNotNull();

	}
	
	@Test
	public void testFindByDocIdAndStatus() {

		HyperMediaLink hml=new HyperMediaLink();
		hml.setLinkAddress("www.testspider.com");
		hml.setLinkType("INTERNAL");
		hml.setStatus(-1);
		
		HyperMediaLink hml_saved=entityManager.persist(hml);
		entityManager.flush();
		
		List<HyperMediaLink> list = 	hmlRepository.findByDocIdAndStatus(hml_saved.getDocId(), -1);
		
		assertThat(list.size()>0);
	}
	
}
