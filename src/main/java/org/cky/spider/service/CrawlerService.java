package org.cky.spider.service;

import java.io.IOException;
import java.util.List;

import org.cky.spider.model.DocumentDetails;
import org.cky.spider.model.HyperMediaLink;

/**
 * @author chandrakumar
 *
 */
public interface CrawlerService {

	DocumentDetails fectchPageDetails(String pageURI) throws IOException;

	void processHyperLinkStatus(List<HyperMediaLink> hyperMediaLinks);

}
