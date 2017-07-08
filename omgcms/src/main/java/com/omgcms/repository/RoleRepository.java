package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.Role;

public interface RoleRepository extends JpaSpecificationExecutor<Role>, JpaRepository<Role, Long> {

	// 根据 roleId 获取角色信息
	public Role getByRoleId(long roleId);

	public Role getByName(String name);

	public Role getByRoleKey(String roleKey);
	
}
