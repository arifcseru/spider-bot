package org.cky.spider.jsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.cky.spider.util.AppConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class CrawlerEngine {

	Document doc;
	String coonectionURI;

	public CrawlerEngine(String coonectionURI) throws IOException {
		super();
		this.doc=Jsoup.connect(coonectionURI).get();
		this.coonectionURI = coonectionURI;

	}

	public Boolean hasLoginForm() {

		Elements newsHeadlines = doc.select("[type='password']");
		if(newsHeadlines!=null && newsHeadlines.size()>0) {
			return true;
		}
		return false;
	}


	public String getHtmlVersion() {

		List<Node> nodes = doc.childNodes();

		return nodes.stream().filter(node -> node instanceof DocumentType).map(this::getHtmlVersion) .collect(Collectors.joining());

	}

	private String getHtmlVersion(Node node) {
		DocumentType documentType = (DocumentType) node;

		String htmlVersion = documentType.attr("publicid");

		return "".equals(htmlVersion) ? "" : htmlVersion;
	}

	public String  getPageTitle() {

		String title = "";
		title =  doc.title();
		return title;
	}


	public HashMap<String,Integer> getHeadLinesCount() {
            HashMap<String,Integer> healinesMap = new HashMap<>();
            Elements elements = doc.select("h1,h2,h3,4,h5,h6");
            elements.parallelStream().forEach(n->{
                    if(healinesMap.containsKey(n.nodeName())) {
                            healinesMap.put(n.nodeName(), healinesMap.get(n.nodeName())+1);
                    }else {
                            healinesMap.put(n.nodeName(),1);
                    }
            });
            return healinesMap;
	}

	public HashMap<String,List<String>> getHiperLinks() {
		String baseUrl = getBaseUrl(coonectionURI);
		HashMap<String,List<String>> linksMap=new HashMap<>();

		Elements docElements = doc.getAllElements();
		List<String> internalLinkList=new LinkedList<String>();
		List<String> externalLinkList=new LinkedList<String>();

		if(docElements!=null) {
		
			docElements.forEach(ele->{

				if(ele.hasAttr("href") || ele.hasAttr("src")) {
					String hrefUrl=null;

					if(ele.hasAttr("href")) {
						hrefUrl=ele.attr("href");	
					}else {
						hrefUrl=ele.attr("src");
					}

					if(hrefUrl!=null && hrefUrl.trim().length()>0) {

						hrefUrl=hrefUrl.trim();
						String linkType=getLinkType(baseUrl,hrefUrl);

						if(AppConstants.LINK_TYPE_EXTERNAL.equalsIgnoreCase(linkType)) {
							externalLinkList.add(hrefUrl);

						}else if(AppConstants.LINK_TYPE_INTERNAL.equalsIgnoreCase(linkType)) {

							if(hrefUrl.startsWith("/") || hrefUrl.startsWith("./")) {
								if(hrefUrl.startsWith("./")) {
									hrefUrl=baseUrl+hrefUrl.substring(1, hrefUrl.length());
								}else {
									hrefUrl=baseUrl+hrefUrl;
								}
							}

							internalLinkList.add(hrefUrl);
						}
					}

				}

			});
		}

		linksMap.put(AppConstants.LINK_TYPE_INTERNAL, internalLinkList);
		linksMap.put(AppConstants.LINK_TYPE_EXTERNAL, externalLinkList);

		return linksMap;

	}

	private String getBaseUrl(String url) {
		URL fullUrl=null;
		try {
			fullUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String baseUrl = fullUrl.getProtocol() + "://" + fullUrl.getHost();

		return baseUrl;
	}

	private String getLinkType(String baseUrl, String hrefUrl) {

		if(hrefUrl!=null && !hrefUrl.startsWith("#") && 
				!hrefUrl.startsWith("javascript") && 
				(hrefUrl.startsWith("http") || 
						hrefUrl.startsWith("https") || 
						hrefUrl.startsWith("/") || hrefUrl.startsWith("./")) ){
			if(hrefUrl.contains(baseUrl) || hrefUrl.startsWith("/") 
					|| hrefUrl.trim().startsWith("./")) {
				return AppConstants.LINK_TYPE_INTERNAL;
			}else {
				return AppConstants.LINK_TYPE_EXTERNAL;
			}

		}else {
			return "INVALID";
		}

	}
}
