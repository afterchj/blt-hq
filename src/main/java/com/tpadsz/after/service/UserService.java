package com.tpadsz.after.service;


import com.tpadsz.after.entity.User;

import java.util.List;

/**
 * @author after
 * @date 2017年1月16日
 */
public interface UserService {
	List<User> selectAll();
	
	User selectById(String id);
	
	User selectByUsername(String username);
	
	void save(User user);
	
	boolean userIsExist(String username);
	
	void deleteById(Integer id);

	void blockUserById(Integer id);

	void unblockUserById(Integer id);
}
