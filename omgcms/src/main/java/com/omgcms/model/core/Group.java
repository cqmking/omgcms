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

@Table(name = "group_")
@Entity
public class Group {
	
	private Long groupId;
	
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
	
	private Set<Role> groupRoles;
	
	@JoinTable(name = "group_role", joinColumns = { @JoinColumn(name = "groupId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	@ManyToMany
	public Set<Role> getGroupRoles() {
		return groupRoles;
	}
	
	public void setGroupRoles(Set<Role> groupRoles) {
		this.groupRoles = groupRoles;
	}
	
	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", allocationSize=1 ,pkColumnName = "name", pkColumnValue = "groupId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
