package com.tpadsz.after.dao;


import com.tpadsz.after.entity.User;

import java.util.List;

public interface UserExtendDao {

	User selectByUsername(String username);
	
	List<String> getRoles(String username);
	
	List<String> getPermissions(String username);
	
}
