package com.tpadsz.after.service;

import com.tpadsz.after.entity.Role;

import java.util.List;

public interface RoleService {

	List<Role> getAll();
	//根据角色名获取id
	Integer getIdByRoleName(String roleName);
	
}
