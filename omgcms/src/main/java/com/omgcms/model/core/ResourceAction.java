package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

@Table(name = "resourceaction", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "resourceName", "resourceType", "actionId" }) })
@Entity
public class ResourceAction implements Serializable {

	private static final long serialVersionUID = -7367037807272901824L;
	
	private Long resourceActionId;

	/**
	 * Resource name, may be className, custom resource id and etc.
	 */
	private String resourceName;

	private String resourceType;

	private String actionId;

	private Long bitwiseValue;
	
	public ResourceAction() {

	}

	public ResourceAction(String resourceName, String resourceType, String actionId, Long bitwiseValue) {
		this.actionId = actionId;
		this.resourceName = resourceName;
		this.resourceType = resourceType;
		this.bitwiseValue = bitwiseValue;
	}

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", initialValue = 1000, allocationSize = 1, pkColumnName = "name", pkColumnValue = "resourceActionId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getResourceActionId() {
		return resourceActionId;
	}

	public void setResourceActionId(Long resourceActionId) {
		this.resourceActionId = resourceActionId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Long getBitwiseValue() {
		return bitwiseValue;
	}

	public void setBitwiseValue(Long bitwiseValue) {
		this.bitwiseValue = bitwiseValue;
	}
	
	@Override
	public String toString() {
		return "ResourceAction [resourceActionId=" + resourceActionId + ", resourceName=" + resourceName + ", actionId=" + actionId
				+ ", bitwiseValue=" + bitwiseValue + "]";
	}

}
