package com.omgcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.omgcms.model.core.Role;

public interface RoleRepository extends JpaSpecificationExecutor<Role>, JpaRepository<Role, Long> {

	/**
	 * Get Role by roleId
	 * @param roleId role id
	 * @return Role
	 */
	public Role getByRoleId(long roleId);

	public Role getByName(String name);

	public Role getByRoleKey(String roleKey);
	
}
