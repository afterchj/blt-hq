package com.tpadsz.after.socket;

import com.tpadsz.after.rabbit.MessageProducer;
import com.tpadsz.after.service.BLTService;
import com.tpadsz.after.utils.PropertiesUtils;
import com.tpadsz.after.utils.SpringUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/21/1.
 */

public class SocketHandler implements Runnable {

    private Logger logger = Logger.getLogger(BLTService.class);
    private static final String ROUTING_KEY = PropertiesUtils.getValue("rabbitmq.key");

    //    private static SqlSessionTemplate sqlSessionTemplate = SpringUtils.getSqlSession();
    private static AmqpTemplate amqpTemplate = SpringUtils.getAmqpTemplate();

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
        Map<String, String> map = new HashMap<>();
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        map.put("ip", ip);
        logger.info("New connection accepted " + ip + ":" + port);
        BufferedReader br = getReader(socket);
        PrintWriter pw = getWriter(socket);
        String msg;
        try {
            while ((msg = br.readLine()) != null) {
                logger.info(msg);
                map.put("msg", msg);
                amqpTemplate.convertAndSend(ROUTING_KEY, msg);
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
            logger.info("status=" + socket.isClosed());
        }
    }
}
