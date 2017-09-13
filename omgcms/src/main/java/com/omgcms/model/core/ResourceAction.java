package com.omgcms.model.core;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	/**
	 * Distinguish the system resource and Entry resource Not use this field,
	 * use PK filed (in ResourcePermission) with 0 for default permission.
	 * Others for instances permission
	 */
	// private Integer type;
	@JsonIgnore
	private Set<ResourcePermission> resourcePermissions;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resourceAction", fetch = FetchType.LAZY)
	public Set<ResourcePermission> getResourcePermissions() {
		return resourcePermissions;
	}

	public void setResourcePermissions(Set<ResourcePermission> resourcePermissions) {
		this.resourcePermissions = resourcePermissions;
	}

	@Override
	public String toString() {
		return "ResourceAction [resourceActionId=" + resourceActionId + ", resourceName=" + resourceName + ", actionId=" + actionId
				+ ", bitwiseValue=" + bitwiseValue + "]";
	}

}
