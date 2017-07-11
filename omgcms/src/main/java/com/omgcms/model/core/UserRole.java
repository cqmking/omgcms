package com.omgcms.model.core;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.omgcms.model.core.pk.UserRolePK;

@Table(name = "user_role")
@Entity
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8330956616457716084L;
	
	private UserRolePK id;

	private User user;

	private Role role;
	
	public UserRole(){
		
	}
	
	public UserRole(UserRolePK id) {
		this.id = id;
	}
	
	public UserRole(long userId, long roleId){
		this.id = new UserRolePK(userId, roleId);
	}
	
	public UserRole(User user, Role role){
		this(user.getUserId(),role.getRoleId());
		this.user = user;
		this.role = role;
	}
	
	@EmbeddedId
	public UserRolePK getId() {
		return id;
	}

	public void setId(UserRolePK id) {
		this.id = id;
	}

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@MapsId("roleId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
