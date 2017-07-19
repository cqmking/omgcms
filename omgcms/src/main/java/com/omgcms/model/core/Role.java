package com.omgcms.model.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "role_")
@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 6824683931019456228L;

	private Long roleId;

	private String name;

	/**
	 * 角色编码(例如 sysadmin->（对应）系统管理员)
	 */
	private String roleKey;

	private String description;

	private Date createDate;

	private Date modifyDate;

	@JsonIgnore
	private Set<UserRole> userRoles;

	@JsonIgnore
	private Set<GroupRole> groupRoles;
	
	@JsonIgnore
	private Set<ResourcePermission> resourcePermissions; 

	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", initialValue = 1000, allocationSize = 1, pkColumnName = "name", pkColumnValue = "roleId", valueColumnName = "value")
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

	@Column(unique = true, nullable = false)
	public void setName(String name) {
		this.name = name;
	}

	@Column(unique = true, nullable = false)
	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
	public Set<GroupRole> getGroupRoles() {
		return groupRoles;
	}

	public void setGroupRoles(Set<GroupRole> groupRoles) {
		this.groupRoles = groupRoles;
	}
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role", fetch = FetchType.LAZY)
	public Set<ResourcePermission> getResourcePermissions() {
		return resourcePermissions;
	}

	public void setResourcePermissions(Set<ResourcePermission> resourcePermissions) {
		this.resourcePermissions = resourcePermissions;
	}

}
