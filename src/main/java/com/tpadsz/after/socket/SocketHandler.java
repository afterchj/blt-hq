package com.tpadsz.after.socket;

import com.tpadsz.after.service.BLTService;
import com.tpadsz.after.utils.DBUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/21/1.
 */

public class SocketHandler implements Runnable {
    private Logger logger = Logger.getLogger(BLTService.class);

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
        map.put("ip", ip);
        logger.info("New connection accepted " + ip + ":" + socket.getPort());
        SqlSessionTemplate sqlSessionTemplate = DBUtils.getSqlSession();
        BufferedReader br = getReader(socket);
        PrintWriter pw = getWriter(socket);
        String msg;
        try {
            while ((msg = br.readLine()) != null) {
                map.put("msg", msg);
                sqlSessionTemplate.insert("light.insertLog", map);
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
        }
    }
}
