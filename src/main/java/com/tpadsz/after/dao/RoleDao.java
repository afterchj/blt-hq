package com.tpadsz.after.dao;


import com.tpadsz.after.entity.Role;

public interface RoleDao {
	int selectIdByName(String roleName);
	
	int deleteById(Integer id);
	
	int insert(Role record);
	
	int insertSelective(Role record);
	
	Role selectById(Integer id);
	
	int updateByIdSelective(Role record);
	
	int updateById(Role record);
	
	
	
}
