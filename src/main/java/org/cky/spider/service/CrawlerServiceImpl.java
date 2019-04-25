package org.cky.spider.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.cky.spider.jsoup.CrawlerEngine;
import org.cky.spider.model.DocumentDetails;
import org.cky.spider.model.HyperMediaLink;
import org.cky.spider.repository.DocumentDetailsRepository;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.cky.spider.util.AppConstants;
import org.springframework.stereotype.Service;

/**
 * @author chandrakumar
 *
 */
@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	DocumentDetailsRepository documentDetailsRepository;
	HyperMediaLinkRepository hyperMediaLinkRepository;

	public CrawlerServiceImpl(DocumentDetailsRepository documentDetailsRepository,
			HyperMediaLinkRepository hyperMediaLinkRepository) {
		super();
		this.documentDetailsRepository = documentDetailsRepository;
		this.hyperMediaLinkRepository = hyperMediaLinkRepository;
	}

	@Override
	public  DocumentDetails fectchPageDetails(String pageURI) throws IOException {
		CrawlerEngine crawlerEngine = new CrawlerEngine(pageURI);
		DocumentDetails dd = new DocumentDetails(); 
		
		dd.setHtmlVersion(crawlerEngine.getHtmlVersion());
		dd.setPageTitle(crawlerEngine.getPageTitle());
		
		dd.setHasLoginForm(crawlerEngine.hasLoginForm());

		HashMap<String, Integer> headLineMap= crawlerEngine.getHeadLinesCount();
		if(headLineMap.containsKey("h1")) {
			dd.setH1(headLineMap.get("h1"));	
		}else {
			dd.setH1(0);
		}
		
		if(headLineMap.containsKey("h2")) {
			dd.setH2(headLineMap.get("h2"));	
		}else {
			dd.setH2(0);
		}
		
		if(headLineMap.containsKey("h3")) {
			dd.setH3(headLineMap.get("h3"));	
		}else {
			dd.setH3(0);
		}
		
		if(headLineMap.containsKey("h4")) {
			dd.setH4(headLineMap.get("h4"));	
		}else {
			dd.setH4(0);
		}
		
		if(headLineMap.containsKey("h5")) {
			dd.setH5(headLineMap.get("h5"));	
		}else {
			dd.setH5(0);
		}
		
		if(headLineMap.containsKey("h6")) {
			dd.setH6(headLineMap.get("h6"));	
		}else {
			dd.setH6(0);
		}

		DocumentDetails ddSaved = documentDetailsRepository.save(dd);
		
		/* set HyperMedia link details */
		setHyperMediaLink(crawlerEngine, ddSaved);
	
		List<HyperMediaLink> savedhyperMediaLinks = hyperMediaLinkRepository.saveAll(ddSaved.getHyperMediaLinks());
		
		ddSaved.setHyperMediaLinks(savedhyperMediaLinks);
		
		return ddSaved;
		
	}
	
	private void setHyperMediaLink(CrawlerEngine crawlerEngine, DocumentDetails dd) {
		HashMap<String, List<String>> hyperLinks= crawlerEngine.getHiperLinks();
		List<HyperMediaLink> hyperMediaLinks=new LinkedList<>(); 
		
		List<String> internalLinks =null;
		List<String> externalLinks =null;
		
		if(hyperLinks.containsKey(AppConstants.LINK_TYPE_INTERNAL)) {
			internalLinks = hyperLinks.get(AppConstants.LINK_TYPE_INTERNAL);	
			dd.setCountInternalLinks(internalLinks.size());
			
			internalLinks.forEach(link->{
				HyperMediaLink hml =new HyperMediaLink();
				hml.setDocId(dd.getId());
				hml.setLinkAddress(link);
				hml.setLinkType(AppConstants.LINK_TYPE_INTERNAL);
				hml.setStatus(-1);
				hyperMediaLinks.add(hml);
			});
			
		}

		if(hyperLinks.containsKey(AppConstants.LINK_TYPE_EXTERNAL)) {
			externalLinks = hyperLinks.get(AppConstants.LINK_TYPE_EXTERNAL);
			dd.setCountExternalLinks(externalLinks.size());
			externalLinks.forEach(link->{
				HyperMediaLink hml =new HyperMediaLink();
				hml.setDocId(dd.getId());
				hml.setLinkAddress(link);
				hml.setLinkType(AppConstants.LINK_TYPE_EXTERNAL);
				hml.setStatus(-1);
				
				hyperMediaLinks.add(hml);
			});
		}
		
		dd.setHyperMediaLinks(hyperMediaLinks);
	}
	
	@Override
	public void processHyperLinkStatus(List<HyperMediaLink> hyperMediaLinks) {
		ExecutorService executor = Executors.newWorkStealingPool(10);
		
		if(hyperMediaLinks!=null) {
			hyperMediaLinks.stream().limit(1000).forEach(hml->{
				executor.execute(new HyperMediaLinkStatusThread(hyperMediaLinkRepository, hml));
			});
		}
		
		executor.shutdown();
		
	}
	
	
	
	
}
