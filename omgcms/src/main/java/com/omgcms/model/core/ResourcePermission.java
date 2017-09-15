package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author luffy
 *
 */
@Table(name = "resourcepermission")
@Entity
public class ResourcePermission implements Serializable {

	private static final long serialVersionUID = -7372463086643243416L;

	private Long resourcePermissionId;

	/**
	 * Resource's PrimaryKey. Resource entry's primary ID, common/system value is 0.
	 */
	private Long primaryKey;

	private String resourceName;
	
	private Long ownerId;

	private Long actionIds;
	
	private Role role;

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", initialValue = 1000, allocationSize = 1, pkColumnName = "name", pkColumnValue = "resourcePermissionId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getResourcePermissionId() {
		return resourcePermissionId;
	}

	public void setResourcePermissionId(Long resourcePermissionId) {
		this.resourcePermissionId = resourcePermissionId;
	}

	public Long getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
	public Long getActionIds() {
		return actionIds;
	}

	public void setActionIds(Long actionIds) {
		this.actionIds = actionIds;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleId", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
