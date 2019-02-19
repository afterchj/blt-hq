package com.tpadsz.after.socket;


import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hongjian.chen on 2019/21/1.
 */
public class EchoServer {

    private static Logger logger = Logger.getLogger(EchoServer.class);
    private static ServerSocket serverSocket = null;
    private static ExecutorService executorService;  //线程池
    private static final int POOL_SIZE = 20;  //单个CPU时线程池中工作线程的数目

    private static String port = PropertiesUtils.getValue("socket.port");

    static {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(port));
            logger.info("服务器已启动...");
        } catch (IOException e) {
            logger.error("serverSocket启动异常：" + e.getMessage());
            try {
                serverSocket.close();
            } catch (IOException e1) {
                logger.error("serverSocket status：" + serverSocket);
            }
        }
        executorService = Executors.newFixedThreadPool(POOL_SIZE);
    }

    private void service() {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                executorService.execute(new SocketHandler(socket));
            } catch (IOException e) {
                logger.error("socket连接失败：" + e.getMessage());
            }
        }
    }

//    public static void main(String[] args) {
//        new EchoServer().service();
//    }
}
