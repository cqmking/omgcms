package com.omgcms.bean.init;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Permission implements Serializable {

	private static final long serialVersionUID = 6245651882456894518L;

	private String type;
	
	@XmlElement(name = "name")
	private String resourceName;
	
	@XmlElement(name = "action")
	private List<String> actions;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	@Override
	public String toString() {
		return "Permission [type=" + type + ", resourceName=" + resourceName + ", actions=" + actions + "]";
	}
	
}
