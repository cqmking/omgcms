package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.omgcms.model.core.pk.GroupRolePK;

@Table(name = "group_role")
@Entity
public class GroupRole implements Serializable {

	private static final long serialVersionUID = -6952274099620580861L;

	private GroupRolePK id;

	private Group group;

	private Role role;
	
	@EmbeddedId
	public GroupRolePK getId() {
		return id;
	}

	public void setId(GroupRolePK id) {
		this.id = id;
	}

	@MapsId("groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="groupId", nullable = false)
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@MapsId("roleId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="roleId", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
