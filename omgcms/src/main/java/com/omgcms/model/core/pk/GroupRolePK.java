package com.omgcms.model.core.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GroupRolePK implements Serializable {

	private static final long serialVersionUID = -5286332049231946837L;

	private Long groupId;

	private Long roleId;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
