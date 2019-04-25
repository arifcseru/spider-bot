package org.cky.spider.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
public class DocumentDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8327656074998147963L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

    private String htmlVersion;
    
    private String pageTitle;
    
    private Boolean hasLoginForm;
    
    private Integer h1;
    
    private Integer h2;
    
    private Integer h3;
    
    private Integer h4;
    
    private Integer h5;
    
    private Integer h6;
    
    private Integer countExternalLinks;
    
    private Integer countInternalLinks;

    @Transient
    List<HyperMediaLink> hyperMediaLinks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHtmlVersion() {
		return htmlVersion;
	}

	public void setHtmlVersion(String htmlVersion) {
		this.htmlVersion = htmlVersion;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Boolean getHasLoginForm() {
		return hasLoginForm;
	}

	public void setHasLoginForm(Boolean hasLoginForm) {
		this.hasLoginForm = hasLoginForm;
	}

	public Integer getH1() {
		return h1;
	}

	public void setH1(Integer h1) {
		this.h1 = h1;
	}

	public Integer getH2() {
		return h2;
	}

	public void setH2(Integer h2) {
		this.h2 = h2;
	}

	public Integer getH3() {
		return h3;
	}

	public void setH3(Integer h3) {
		this.h3 = h3;
	}

	public Integer getH4() {
		return h4;
	}

	public void setH4(Integer h4) {
		this.h4 = h4;
	}

	public Integer getH5() {
		return h5;
	}

	public void setH5(Integer h5) {
		this.h5 = h5;
	}

	public Integer getH6() {
		return h6;
	}

	public void setH6(Integer h6) {
		this.h6 = h6;
	}

	public Integer getCountExternalLinks() {
		return countExternalLinks;
	}

	public void setCountExternalLinks(Integer countExternalLinks) {
		this.countExternalLinks = countExternalLinks;
	}

	public Integer getCountInternalLinks() {
		return countInternalLinks;
	}

	public void setCountInternalLinks(Integer countInternalLinks) {
		this.countInternalLinks = countInternalLinks;
	}

	public List<HyperMediaLink> getHyperMediaLinks() {
		return hyperMediaLinks;
	}

	public void setHyperMediaLinks(List<HyperMediaLink> hyperMediaLinks) {
		this.hyperMediaLinks = hyperMediaLinks;
	}
	
}
