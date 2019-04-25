package org.scout24.spider;



import static org.assertj.core.api.Assertions.assertThat;

import org.cky.spider.model.DocumentDetails;
import org.cky.spider.repository.DocumentDetailsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class DocumentDetailsRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private DocumentDetailsRepository documentDetailsRepository;

	@Test
	public void whenSaved_thenReturnDocumentDetail() {
		// given

		DocumentDetails dd = new DocumentDetails();

		dd.setPageTitle("JUNIT TEST");
		dd.setHasLoginForm(true);
		dd.setHtmlVersion("HTML4");
		dd.setH1(1);
		dd.setH2(1);
		dd.setH3(1);
	
		DocumentDetails saved = documentDetailsRepository.save(dd);

		assertThat(saved).isNotNull();

	}
	
	@Test
	public void testDelete() {
		// given

		DocumentDetails dd = new DocumentDetails();

		dd.setPageTitle("JUNIT Delete TEST");
		dd.setHasLoginForm(true);
		dd.setHtmlVersion("HTML4");
		dd.setH1(1);
		dd.setH2(1);
		dd.setH3(1);

		entityManager.persist(dd);
		entityManager.flush();
		
		dd = new DocumentDetails();
		dd.setPageTitle("JUNIT Delete TEST 2");
		dd.setHasLoginForm(true);
		dd.setHtmlVersion("HTML4");
		dd.setH1(1);
		dd.setH2(1);
		dd.setH3(1);

		DocumentDetails last_saved = entityManager.persist(dd);
		entityManager.flush();
		
		long countBefore= (long) entityManager.getEntityManager().createQuery(" SELECT count(1) from DocumentDetails").getSingleResult();
		
		documentDetailsRepository.deleteOldRecords(last_saved.getId());

		long countAfter= (long) entityManager.getEntityManager().createQuery(" SELECT count(1) from DocumentDetails").getSingleResult();
		
		assertThat(countBefore).isEqualTo(countAfter+1);
		
	}
	
	@Test
	public void testMaxId() {
		// given

		DocumentDetails dd = new DocumentDetails();

		dd.setPageTitle("JUNIT Delete TEST");
		dd.setHasLoginForm(true);
		dd.setHtmlVersion("HTML4");
		dd.setH1(1);
		dd.setH2(1);
		dd.setH3(1);

		entityManager.persist(dd);
		entityManager.flush();
		
		dd = new DocumentDetails();
		dd.setPageTitle("JUNIT Delete TEST 2");
		dd.setHasLoginForm(true);
		dd.setHtmlVersion("HTML4");
		dd.setH1(1);
		dd.setH2(1);
		dd.setH3(1);

		DocumentDetails last_saved = entityManager.persist(dd);
		entityManager.flush();
		
		Long maxId = documentDetailsRepository.getMaxId();
		
		assertThat(last_saved.getId()).isEqualTo(maxId);
		
	}
	
}
