package com.tpadsz.after;

import com.tpadsz.after.entity.User;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2018/8/1.
 */
public class Main {

//    public static void main(String[] args) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        SqlSessionTemplate sessionTemplate= (SqlSessionTemplate) ctx.getBean("sqlSessionTemplate");
//        System.out.println(sessionTemplate.selectList("com.tpadsz.after.dao.SysUserDao.getAll").size());
//    }

    @Test
    public void testStr() {
        String str = "77040E022AE5010000C000020400000000";
        System.out.println(str.length());
        List list = new ArrayList();
        System.out.println(list.size());
    }

    private SqlSessionTemplate getSqlSessionTemplate() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        SqlSessionTemplate sqlSessionTemplate = (SqlSessionTemplate) ctx.getBean("sqlSessionTemplate");
        return sqlSessionTemplate;
    }

    @Test
    public void testSqlSessionTemplate() {
        Map map = new HashMap();
        map.put("uname", "管理员");
        map.put("account", "admin");
        User user = getSqlSessionTemplate().selectOne("com.tpadsz.after.dao.UserExtendDao.selectByUsername", "超级管理员");
        System.out.println("user:" + user.toString());
    }
}
