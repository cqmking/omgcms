package com.omgcms.model.core.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserGroupPK implements Serializable {

	private static final long serialVersionUID = 3907253555264995042L;

	private Long userId;

	private Long groupId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
