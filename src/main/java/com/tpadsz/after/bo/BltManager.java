package com.tpadsz.after.bo;

import com.tpadsz.after.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongjian.chen on 2019/2/19.
 */

public class BltManager {

    private static Logger logger = Logger.getLogger(BltManager.class);
    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();

    public static void saveMap(String msg, String ip) {
        int len = msg.length();
        if (len >= 36 && len <= 40) {
            tempFormat(msg);
        } else if (len > 40) {
            formatStr(msg);
        } else {
            Map map = new HashMap();
            map.put("ip", ip);
            map.put("msg", msg);
            sqlSessionTemplate.insert("light.insertLog", map);
        }
    }

    public static void tempFormat(String format) {
        String str = format.substring(18, format.length());
        int len = str.length();
        String prefix = str.substring(0, 2).toUpperCase();
        Map map = new ConcurrentHashMap<>();
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
                if ("C1".equals(prefix) || "C4".equals(prefix) || "71".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", str.substring(2, 4));
                    map.put("y", str.substring(4, 6));
                    if (!"71".equals(prefix)) {
                        map.put("cid", str.substring(len - 4, len - 2));
                    }
                }
                break;
        }
//        amqpTemplate.convertAndSend(ROUTING_KEY, JSON.toJSONString(map));
        sqlSessionTemplate.selectOne("light.saveConsole", map);
        logger.info("result=" + map.get("result"));
    }

    private static void formatStr(String str) {
        Map map = new ConcurrentHashMap<>();
        String prefix = str.substring(0, 2);
        map.put("prefix_value", prefix);
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
                if ("03".equals(prefix)) {
                    map.put("ctype", str.substring(34, 36));
                    map.put("cid", str.substring(36, 38));
                    map.put("x", str.substring(38, 40));
                    map.put("y", str.substring(40, str.length()));
                }
                break;
        }
        sqlSessionTemplate.selectOne("light.saveConsole", map);
        logger.info("result=" + map.get("result"));
    }
}
