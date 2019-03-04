package com.tpadsz.after.service.impl;

import com.luoxiao.dao.RoleDao;
import com.luoxiao.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Integer getIdByRoleName(String roleName) {
		return roleDao.selectIdByName(roleName);
	}

}
