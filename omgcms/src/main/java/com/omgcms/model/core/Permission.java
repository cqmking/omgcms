package com.omgcms.model.core;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "permission")
@Entity
public class Permission {
	
	private Long permissionId;
	
	private String permissionKey;
	
	private String name;

	private String description;

	/**
	 * 创建者的用户userId
	 */
	private Long userId;

	/**
	 * 创建者的用户账号
	 */
	private String userAccount;

	private Date createDate;

	private Date modifyDate;
	
	private Set<Role> roles;
	
	@ManyToMany(mappedBy="permissions")
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", allocationSize = 1, pkColumnName = "name", pkColumnValue = "permissionId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getPermissionId() {
		return permissionId;
	}
	
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
	@Column(name = "permissionKey", unique = true, nullable = false)
	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
