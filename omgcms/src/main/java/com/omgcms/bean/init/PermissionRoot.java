package com.omgcms.bean.init;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "permissions")
public class PermissionRoot implements Serializable {

	private static final long serialVersionUID = 7172654267674922051L;

	@XmlElement(name = "import")
	private List<ImportResource> resources;
	
	@XmlElement(name = "permission")
	private List<Permission> permissions;
	
	public List<ImportResource> getResources() {
		return resources;
	}

	public void setResources(List<ImportResource> resources) {
		this.resources = resources;
	}
	
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}


