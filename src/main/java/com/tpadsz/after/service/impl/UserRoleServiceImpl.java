package com.tpadsz.after.service.impl;

import com.tpadsz.after.dao.UserRoleDao;
import com.tpadsz.after.entity.UserRole;
import com.tpadsz.after.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author losho
 * @date 2017年1月10日
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public UserRole selectByUserId(Integer userId) {
		UserRole userRole = userRoleDao.selectByUserId(userId);
		return userRole;
	}

	@Override
	public void insert(UserRole record) {
		userRoleDao.insert(record);
	}

	@Override
	public void deleteById(Integer id) {
		userRoleDao.deleteById(id);
		
	}

}
