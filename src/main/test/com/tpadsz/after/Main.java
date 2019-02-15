package com.tpadsz.after;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by hongjian.chen on 2018/8/1.
 */
public class Main {

//    public static void main(String[] args) {
//        ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//        SqlSessionTemplate sessionTemplate= (SqlSessionTemplate) ctx.getBean("sqlSessionTemplate");
//        System.out.println(sessionTemplate.selectList("com.tpadsz.after.dao.UserDao.getAll").size());
//    }

    @Test
    public void testStr(){
        String str="77040E022AE5010000C000020400000000";
        System.out.println(str.length());
    }
}
