package com.omgcms.bean;

import java.util.List;

public class NavigationItem {

	private Integer parentId;

	private Integer id;

	private String url;

	private String code;

	private String target;

	private String cssClass;

	private String iconClass;

	private List<NavigationItem> subNavigations;

	public NavigationItem() {
		super();
	}

	public NavigationItem(Integer parentId, Integer id, String url, String code, String target, String cssClass, String iconClass) {
		super();
		this.parentId = parentId;
		this.id = id;
		this.url = url;
		this.code = code;
		this.target = target;
		this.cssClass = cssClass;
		this.iconClass = iconClass;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public List<NavigationItem> getSubNavigations() {
		return subNavigations;
	}

	public void setSubNavigations(List<NavigationItem> subNavigations) {
		this.subNavigations = subNavigations;
	}

	@Override
	public String toString() {
		return "NavigationItem [id=" + id + ", url=" + url + ", code=" + code + ", target=" + target + ", cssClass=" + cssClass
				+ ", iconClass=" + iconClass + "]";
	}

}
