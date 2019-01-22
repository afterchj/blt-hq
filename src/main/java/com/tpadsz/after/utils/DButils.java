package com.tpadsz.after.utils;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hongjian.chen on 2019/1/22.
 */
public class DButils {

    private static SqlSessionTemplate sqlSessionTemplate;

    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        sqlSessionTemplate = (SqlSessionTemplate) ctx.getBean("sqlSessionTemplate");
    }

    public static SqlSessionTemplate getSqlSession() {
        return sqlSessionTemplate;
    }
}
