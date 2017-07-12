package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "resourceaction")
@Entity
public class ResourceAction implements Serializable {

	private static final long serialVersionUID = -7367037807272901824L;

	private Long resourceActionId;

	/**
	 * Resource name, may be className, custom resource id and etc.
	 */
	private Long resourceName;

	private String actionId;

	/**
	 * Distinguish the system resource and Entry resource
	 */
	private Integer type;

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", initialValue = 1000, allocationSize = 1, pkColumnName = "name", pkColumnValue = "resourceActionId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getResourceActionId() {
		return resourceActionId;
	}

	public void setResourceActionId(Long resourceActionId) {
		this.resourceActionId = resourceActionId;
	}

	public Long getResourceName() {
		return resourceName;
	}

	public void setResourceName(Long resourceName) {
		this.resourceName = resourceName;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
