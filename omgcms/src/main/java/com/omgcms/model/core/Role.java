package com.omgcms.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "role_")
@Entity
public class Role implements Serializable{
	
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
