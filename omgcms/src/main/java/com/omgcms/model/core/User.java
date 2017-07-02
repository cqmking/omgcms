package com.omgcms.model.core;

import java.io.Serializable;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "user_")
@Entity
public class User implements Serializable{
	
	private static final long serialVersionUID = 4638652228669149914L;
	
	private Long userId;

	private String userAccount;

	private String userName;

	private String password;
	
	private String sex;

	private String email;

	private Integer age;

	private Date birthday;

	private String phone;

	private Date lastLoginDate;

	private String description;

	private String address;
	
	private String salt;

	private Date createDate;

	private Date modifyDate;
	
	@JsonIgnore
	private Set<Role> userRoles;
	
	@JsonIgnore
	private Set<Group> userGroups;
	
	@TableGenerator(name = "ID_GENERATOR", table = "idgenerator", allocationSize = 1, pkColumnName = "name", pkColumnValue = "userId", valueColumnName = "value")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
	@Id
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "user_account", unique = true, nullable = false)
	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "email", unique = true, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	@Column(name = "description", length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "roleId") })
	@ManyToMany
	public Set<Role> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	@JoinTable(name = "user_group", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = {
			@JoinColumn(name = "groupId") })
	@ManyToMany
	public Set<Group> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<Group> userGroups) {
		this.userGroups = userGroups;
	}
	
}
