package org.cky.spider.controller;

import java.io.IOException;
import java.util.List;

import org.cky.spider.model.DocumentDetails;
import org.cky.spider.model.HyperMediaLink;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.cky.spider.service.CrawlerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CrawlerController {

	CrawlerService crawlerService; 
	HyperMediaLinkRepository hyperMediaLinkRepository;

	public CrawlerController(CrawlerService crawlerService,HyperMediaLinkRepository hyperMediaLinkRepository) {
		super();
		this.crawlerService = crawlerService;
		this.hyperMediaLinkRepository=hyperMediaLinkRepository;

	}

	@PostMapping("/search")
	public String fecthDetails(Model model,@RequestParam("pageURI") String pageURI) throws IOException  {
		System.out.println(" pageURI = " + pageURI);

		DocumentDetails documentDetails=null;

		documentDetails = crawlerService.fectchPageDetails(pageURI);
		model.addAttribute("documentDetails",documentDetails);
		model.addAttribute("hyperMediaLinks",documentDetails.getHyperMediaLinks());
		model.addAttribute("pageURI",pageURI);

		crawlerService.processHyperLinkStatus(documentDetails.getHyperMediaLinks()); 


		return "result";
	}

	@ResponseBody
	@GetMapping("/checkIfCompleted")
	public Boolean checkIfCompleted(@RequestParam Long docId, @RequestParam Integer counter){
		
		List<HyperMediaLink> list = 	hyperMediaLinkRepository.findByDocIdAndStatus(docId, -1);

		if(counter==20) {
			list.forEach(hml->{
				hml.setStatus(504);
				hml.setErrorMessage("Timeout error");
			});

			// set timeout status to stop loading in UI 
			hyperMediaLinkRepository.saveAll(list);
			System.out.println("forcefully stopped.");
			return true;
		}else if(list==null || list.size()==0 ) {
			System.out.println("List empty .");
			return true;
		}

		return false;
	}

	@GetMapping("/refreshLinks")
	public String refreshLinks(Model model, @RequestParam Long docId){
		System.out.println("docId= " +docId );

		List<HyperMediaLink> list = 	hyperMediaLinkRepository.findByDocId(docId);

		model.addAttribute("hyperMediaLinks",list);

		return "linkDetails";

	}
}