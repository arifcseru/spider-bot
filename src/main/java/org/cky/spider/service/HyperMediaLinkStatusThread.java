package org.cky.spider.service;

import org.jsoup.Connection.Response;
import org.cky.spider.model.HyperMediaLink;
import org.cky.spider.repository.HyperMediaLinkRepository;
import org.jsoup.Jsoup;

/**
 * @author chandrakumar
 *
 */
public class HyperMediaLinkStatusThread implements Runnable{


	private final HyperMediaLinkRepository  hyperMediaLinkRepository;
	private  HyperMediaLink hyperMediaLink;

	public HyperMediaLinkStatusThread(HyperMediaLinkRepository hyperMediaLinkRepository, HyperMediaLink hyperMediaLink) {
		super();
		this.hyperMediaLinkRepository = hyperMediaLinkRepository;
		this.hyperMediaLink = hyperMediaLink;

	}

	@Override
	public void run() {

		try {
			//System.out.println(" docId="+this.hyperMediaLink.getDocId());
			//System.out.println(" thread hit "+this.hyperMediaLink.getId());
			
			Response response = Jsoup.connect(this.hyperMediaLink.getLinkAddress()).followRedirects(false)
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
					.ignoreContentType(true)
					.timeout(50000)
					.execute();
			
			if(response!=null) {
				
					this.hyperMediaLink.setStatus(response.statusCode());
					this.hyperMediaLink.setErrorMessage(response.statusMessage());
				
			}else {
				this.hyperMediaLink.setStatus(520);
				this.hyperMediaLink.setErrorMessage("Unwnown error");
			}
			
		}catch(Exception ex) {
			this.hyperMediaLink.setStatus(500);
			this.hyperMediaLink.setErrorMessage(ex.getMessage());
			ex.printStackTrace();
		}

		try {
			hyperMediaLinkRepository.saveAndFlush(this.hyperMediaLink);	
		}catch(Exception ex) {
			ex.printStackTrace();
		}


	}

}
