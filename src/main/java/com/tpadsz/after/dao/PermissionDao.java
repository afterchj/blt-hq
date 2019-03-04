package com.tpadsz.after.dao;


import com.tpadsz.after.entity.Permission;

/**
 * @author after
 * @date 2017年1月10日
 */
public interface PermissionDao {

	int deleteById(Integer id);
	
	int insert(Permission record);
	
	int insertSelective(Permission record);
	
	Permission selectById(Integer id);
	
	int updateByIdSelective(Permission record);
	
	int updateById(Permission record);
	
}
