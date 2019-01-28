package com.tpadsz.after.socket;

import com.tpadsz.after.rabbit.MessageProducer;
import com.tpadsz.after.service.BLTService;
import com.tpadsz.after.utils.PropertiesUtils;
import com.tpadsz.after.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongjian.chen on 2019/21/1.
 */

public class SocketHandler implements Runnable {

    private Logger logger = Logger.getLogger(BLTService.class);
    private static final String ROUTING_KEY = PropertiesUtils.getValue("rabbitmq.key");

    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
//    private static AmqpTemplate amqpTemplate = SpringUtils.getAmqpTemplate();

    private Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter getWriter(Socket socket) {
        OutputStream socketOut;
        try {
            socketOut = socket.getOutputStream();
            return new PrintWriter(new OutputStreamWriter(socketOut, "UTF-8"), true);
        } catch (IOException e) {
            logger.error("获取输出流异常！" + e.getMessage());
        }
        return null;
    }

    private BufferedReader getReader(Socket socket) {
        InputStream socketIn;
        try {
            socketIn = socket.getInputStream();
            return new BufferedReader(new InputStreamReader(socketIn, "UTF-8"));
        } catch (IOException e) {
            logger.error("获取输入流异常！" + e.getMessage());
        }
        return null;
    }

    public String echo(String msg) {
        return "receive:" + msg;
    }


    public void run() {
//        Map map = new HashMap<>();
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
//        map.put("ip", ip);
        logger.info("New connection accepted " + ip + ":" + port);
        BufferedReader br = getReader(socket);
        PrintWriter pw = getWriter(socket);
        String msg;
        try {
            while ((msg = br.readLine()) != null) {
                int len = msg.length();
                if (len >= 36 && len <= 40) {
                    tempFormat(msg);
                }
                if (len > 40) {
                    formatStr(msg);
                }
//                map.put("msg", msg);
//                amqpTemplate.convertAndSend(ROUTING_KEY, msg);
//                sqlSessionTemplate.insert("light.insertLog", map);
                pw.println(echo(msg));
            }
        } catch (IOException e) {
            logger.info("status:" + e.getMessage());
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            logger.info("isClosed=" + socket.isClosed());
        }
    }

    private void tempFormat(String format) {
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
                if ("C1".equals(prefix) || "C4".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", str.substring(2, 4));
                    map.put("y", str.substring(4, 6));
                    map.put("cid", str.substring(len - 4, len - 2));
                }
                if ("71".equals(prefix)) {
                    map.put("ctype", prefix);
                    map.put("x", str.substring(2, 4));
                    map.put("y", str.substring(4, 6));
                    map.put("cid", "");
                }
                break;
        }
        sqlSessionTemplate.selectOne("light.saveConsole", map);
        logger.info("result=" + map.get("result"));
    }

    private void formatStr(String str) {
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
