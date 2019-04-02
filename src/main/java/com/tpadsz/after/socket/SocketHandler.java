package com.tpadsz.after.socket;

import com.tpadsz.after.bo.BltManager;
import org.apache.log4j.Logger;


import java.io.*;
import java.net.Socket;

/**
 * Created by hongjian.chen on 2019/21/1.
 */

public class SocketHandler implements Runnable {

    private Logger logger = Logger.getLogger(SocketHandler.class);
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

    public void run() {
        String ip = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        logger.info("New connection accepted " + ip + ":" + port);
        BufferedReader br = getReader(socket);
        PrintWriter pw = getWriter(socket);
        String msg;
        try {
            while ((msg = br.readLine()) != null) {
                BltManager.saveMap(msg, ip);
                logger.info("from server:" + msg);
                pw.println(msg);
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
}
