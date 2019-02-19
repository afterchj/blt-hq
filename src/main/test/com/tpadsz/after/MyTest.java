package com.tpadsz.after;

import com.alibaba.fastjson.JSON;
import com.tpadsz.after.utils.SpringUtils;
import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.amqp.core.AmqpTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
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


//    @Test
//    public void testRabbit() throws InterruptedException {
////        Thread.sleep(3000);
//        MessageProducer messageProducer = SpringUtils.getProducer();
//        AmqpTemplate amqpTemplate = SpringUtils.getAmqpTemplate();
//        System.out.println("result=" + amqpTemplate + "\t" + messageProducer);
//        Map map = new HashMap();
//        map.put("ip", "127.0.0.1");
//        for (int i = 1; i < 21; i++) {
//            map.put("msg", "i = " + i);
//            messageProducer.sendMsg(JSON.toJSONString(map));
//            messageProducer.sendMsg1(JSON.toJSONString(map));
////            amqpTemplate.convertAndSend("blt_light", "localhost test" + i);
//        }
//    }

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
        System.out.println("flag=" + "c4".toUpperCase().equals("C4"));
        String str = "77 04 0F 02 27 35 00 00 00 71 00 13 00 00 00 00 00 00 0E";
        String c4 = "77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F".replace(" ", "");
        String str52 = "77 04 10 02 21 69 00 00 00 52 77 65 65 D7 AC F0 00 02 00 85".replace(" ", "");
        String format = "77 04 0E 02 2A 9D 01 00 00 C0 00 37 37 00 00 00 00 09".replace(" ", "");
        System.out.println(format + "\t" + format.length());
        System.out.println("77040F0227250000007132320000000000000D".length());
        System.out.println("77040F02272500000071020B00000000000004".length());
        String info = "01F0ACD700950108080808F0ACD700920300010304";
        String statusInfo = "02F0ACD700950108080808F0ACD700920301101000";
        String cmd = "03F0ACD700950108080808000000000000c1011010";
        String prefix = format.substring(18, 20);
        Map<String, String> map = tempFormat(prefix, format.substring(18, format.length()));
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
//        }
    }

    public Map tempFormat(String prefix, String str) {
        int len = str.length();
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("prefix_value", "03");
        switch (prefix) {
            case "52":
                map.put("ctype", prefix);
                map.put("cid", str.substring(len - 6, len - 4));
                break;
            case "C0":
                map.put("ctype", prefix);
                map.put("x", str.substring(4, 6));
                map.put("y", str.substring(6, 8));
                break;
            case "42":
                map.put("ctype", prefix);
                map.put("cid", str.substring(len - 4, len - 2));
                break;
            default:
                map.put("ctype", prefix);
                map.put("x", str.substring(2, 4));
                map.put("y", str.substring(4, 6));
                if ("71".equals(prefix)) {
                    map.put("cid", "");
                } else {
                    map.put("cid", str.substring(len - 4, len - 2));
                }
                break;
        }
        return map;
    }

    public Map formatStr(String prefix, String str) {
        Map map = new ConcurrentHashMap<>();
        map.put("prefix_value", str.substring(0, 2));
        map.put("dmac", str.substring(2, 14));
        map.put("mesh_id", str.substring(14, 22));
        map.put("lmac", str.substring(22, 34));
        map.put("GID", str.substring(34, 36));
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

    @Test
    public void testDB() {
        SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
//        System.out.println(sqlSessionTemplate.selectList("light.getLights").size());
//        String 71 = "77040F0227350000007100130000000000000E";
        String c4 = "77 04 10 02 20 95 00 00 00 C4 5F 02 00 00 00 00 00 00 02 4F".replace(" ", "");
        String c1 = "77 04 10 02 20 9D 01 00 00 C1 32 32 00 00 00 00 00 00 02 1E".replace(" ", "");
        String str = "77 04 0E 02 2A 9D 01 00 00 C0 00 37 37 00 00 00 00 09".replace(" ", "");
        String info = "01F0ACD700950108080808F0ACD700920300010304";
        String statusInfo = "02F0ACD700950108080808F0ACD700920301101000";
        String cmd = "03F0ACD700950108080808000000000000c1011010";
//        Map pram = formatStr("01", info);
        String prefix = str.substring(18, 20);
        Map pram = tempFormat(prefix, str.substring(18, str.length()));
        sqlSessionTemplate.selectOne("light.saveConsole", pram);
        System.out.println("result=" + pram.get("result"));
    }

}