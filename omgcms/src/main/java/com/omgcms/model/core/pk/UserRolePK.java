package com.omgcms.model.core.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserRolePK implements Serializable {

	private static final long serialVersionUID = -5631322321421347742L;

	private Long userId;

	private Long roleId;

	public UserRolePK() {
	}
	
	public UserRolePK(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
