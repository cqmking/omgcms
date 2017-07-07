package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "resourcepermission")
@Entity
public class ResourcePermission implements Serializable{
	
	private static final long serialVersionUID = -7372463086643243416L;

	private Long resourcePermissionId;
	
	private Long resourceActionId;
	
	/**
	 * Resource's PrimaryKey
	 */
	private Long primaryKey;
	
	private Long roleId;

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", allocationSize = 1, pkColumnName = "name", pkColumnValue = "resourcePermissionId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getResourcePermissionId() {
		return resourcePermissionId;
	}
	
	public void setResourcePermissionId(Long resourcePermissionId) {
		this.resourcePermissionId = resourcePermissionId;
	}
	
	public Long getResourceActionId() {
		return resourceActionId;
	}

	public void setResourceActionId(Long resourceActionId) {
		this.resourceActionId = resourceActionId;
	}

	public Long getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
}
