package com.tpadsz.after.service.impl;


import com.tpadsz.after.dao.UserDao;
import com.tpadsz.after.entity.User;
import com.tpadsz.after.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User selectById(String id) {
		User user = userDao.selectById(id);
		return user;
	}

	@Override
	public void save(User user) {
		userDao.insert(user);

	}

	@Override
	public User selectByUsername(String username) {
		return userDao.selectByUsername(username);
	}

	@Override
	public boolean userIsExist(String username) {
		User user = userDao.selectByUsername(username);
		if(user == null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List<User> selectAll() {
		return userDao.selectAll();
	}

	@Override
	public void deleteById(Integer id) {
		userDao.deleteById(id);
	}

	@Override
	public void blockUserById(Integer id) {
		userDao.blockUserById(id);
	}

	@Override
	public void unblockUserById(Integer id) {
		userDao.unblockUserById(id);
	}

}
