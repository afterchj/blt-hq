package com.tpadsz.after.service.impl;

import com.tpadsz.after.dao.RoleDao;
import com.tpadsz.after.entity.Role;
import com.tpadsz.after.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;

	@Override
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public Integer getIdByRoleName(String roleName) {
		return roleDao.selectIdByName(roleName);
	}

}
