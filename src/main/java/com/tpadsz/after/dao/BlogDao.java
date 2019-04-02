package com.tpadsz.after.dao;

import com.tpadsz.after.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author after
 * @date 2017年2月9日
 */
public interface BlogDao {
	//提交博客
	void insertBlog(Blog blog);
	
	void deleteById(Integer id);
	
	Blog selectById(Integer id);
	
	//根据用户ID获取博客列表
	List<Blog> selectAllByUserId(@Param("userId") Integer userId, @Param("page") int page, @Param("rows") int rows);
	
	//搜索博客
	List<Blog> selectByKeyword(@Param("keyword") String keyword, @Param("page") int page, @Param("rows") int rows);

	//所有博客
	List<Blog> selectAllBlog(@Param("page") int page, @Param("rows") int rows);
}
