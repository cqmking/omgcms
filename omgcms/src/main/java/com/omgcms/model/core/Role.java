package com.omgcms.model.core;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "role_")
@Entity
public class Role {
	
	private Long roleId;

	private String name;
	
	private String roleKey;

	private String description;
	
	/**
	 * 创建该角色的用户userId
	 */
	private Long userId;

	/**
	 * 创建该角色的用户账号
	 */
	private String userAccount;

	private Date createDate;

	private Date modifyDate;
	
	private Set<Permission> permissions;
	

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", allocationSize = 1, pkColumnName = "name", pkColumnValue = "roleId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "roleKey", unique = true, nullable = false)
	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
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
	
	// referencedColumnName 当前表的字段
	@JoinTable(name = "role_permission", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = {
			@JoinColumn(name = "permissionId") })
	@ManyToMany
	public Set<Permission> getPermissions() {
		return permissions;
	}
	
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	

}
