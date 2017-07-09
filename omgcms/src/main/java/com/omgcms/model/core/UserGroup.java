package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.omgcms.model.core.pk.UserGroupPK;

@Table(name = "user_group")
@Entity
public class UserGroup implements Serializable{
	
	private static final long serialVersionUID = 2686048650737601826L;
	
	private UserGroupPK id;
	
	private User user;
	
	private Group group;
	
	@EmbeddedId
	public UserGroupPK getId() {
		return id;
	}

	public void setId(UserGroupPK id) {
		this.id = id;
	}
	
	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@MapsId("groupId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="group_id", nullable = false)
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
}
