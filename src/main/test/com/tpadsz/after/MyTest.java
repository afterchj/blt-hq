package com.tpadsz.after;

import com.tpadsz.after.rabbit.MessageProducer;
import com.tpadsz.after.utils.SpringUtils;
import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.amqp.core.AmqpTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
                if (method.getName().equals("service")) {
                    method.setAccessible(true);
                    method.invoke(object);
                }
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
        SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
        System.out.println(sqlSessionTemplate.selectList("light.getLights").size());
    }

    @Test
    public void testRabbit() throws InterruptedException {
//        Thread.sleep(3000);
        MessageProducer messageProducer = SpringUtils.getProducer();
        AmqpTemplate amqpTemplate = SpringUtils.getAmqpTemplate();
        System.out.println("amqpTemplate=" + messageProducer);
        for (int i = 1; i < 101; i++) {
//            amqpTemplate.convertAndSend("blt_light","blt_queue send message " + i);
//            messageProducer.sendMsg("blt_queue send message " + i);
            if (i % 2 == 0) {
                messageProducer.send(i);
            } else if (i % 3 == 0) {
                messageProducer.send1(i);
            } else {
                messageProducer.sendMsg("direct_exchange send message " + i);
            }
        }
        Thread.sleep(3000);
    }

    @Test
    public void inverse() {
        Integer n1 = 55;
        String hex = n1.toHexString(n1);
        System.out.println(hex);
        System.out.println("---------------分割线---------------");
        String o_hex = "ff";
        System.out.println(Integer.parseInt(o_hex, 16));
        System.out.println("---------------分割线---------------");
        String p_hex = "0xabc";
        Integer o = Integer.parseInt(p_hex.substring(2), 16);//从第2个字符开始截取
        System.out.println(o);
    }

    @Test
    public void testSubstrix() {
        String info = "01F0ACD700950108080808F0ACD700920300010304";
        String statusInfo = "02F0ACD700950108080808F0ACD700920301101000";
        String cmd = "03F0ACD700950108080808000000000000c1011010";
        String prefix = cmd.substring(0, 2);
        Map<String, String> map = formatStr(prefix, cmd);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }

    public Map<String, String> formatStr(String prefix, String str) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("prefix_value", str.substring(0, 2));
        map.put("dmac", str.substring(2, 14));
        map.put("mesh_id", str.substring(14, 22));
        map.put("lmac", str.substring(22, 34));

        switch (prefix) {
            case "01":
                map.put("code", str.substring(34, 38));
                map.put("code_version", str.substring(38, str.length()));
                break;
            case "02":
                map.put("GID", str.substring(34, 36));
                map.put("x", str.substring(36, 38));
                map.put("y", str.substring(38, 40));
                map.put("suffix_value", str.substring(40, str.length()));
                break;
            default:
                map.put("ctype", str.substring(34, 36));
                map.put("cid", str.substring(36, 38));
                map.put("x", str.substring(38, 40));
                map.put("y", str.substring(40, str.length()));
                break;
        }
//        if ("01".equals(prefix)) {
//            map.put("code", str.substring(34, 38));
//            map.put("code_version", str.substring(38, str.length()));
//        } else if ("02".equals(prefix)) {
//            map.put("GID", str.substring(34, 36));
//            map.put("cid", str.substring(36, 38));
//            map.put("x", str.substring(38, 40));
//            map.put("y", str.substring(40, str.length()));
//        } else {
//            map.put("ctype", str.substring(34, 36));
//            map.put("cid", str.substring(36, 38));
//            map.put("x", str.substring(38, 40));
//            map.put("y", str.substring(40, str.length()));
//        }
        return map;
    }

}