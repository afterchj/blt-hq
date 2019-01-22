package com.tpadsz.after;

import com.tpadsz.after.socket.EchoServer;
import com.tpadsz.after.utils.DButils;
import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by hongjian.chen on 2018/8/1.
 */
public class MyTest {
    private static Logger logger = Logger.getLogger(MyTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println("This is println message.");

        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }


    @Test
    public void test1() {
        Class clz;
        try {
            clz = Class.forName(PropertiesUtils.getValue("className"));
            Method[] methods = clz.getDeclaredMethods();
            Constructor constructor = clz.getConstructor();
            Object object = constructor.newInstance();
            for (Method method : methods) {
                if (method.getName().equals("service")){
                    method.setAccessible(true);
                    method.invoke(object);
                }
                System.out.println(method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    @Test
    public void testSingleton() {
//        EchoServer.getInstance().service();
    }

    @Test
    public void testDB() {
        SqlSessionTemplate sqlSessionTemplate = DButils.getSqlSession();
        System.out.println(sqlSessionTemplate.selectList("light.getLights").size());
    }
}