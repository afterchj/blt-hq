package com.tpadsz.after.socket;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hongjian.chen on 2019/21/1.
 */
public class EchoServer {

    private static Logger logger = Logger.getLogger(EchoServer.class);
    private static EchoServer instance;
    private static ServerSocket serverSocket = null;
    private static ExecutorService executorService;  //线程池
    private static final int POOL_SIZE = 100;  //单个CPU时线程池中工作线程的数目

    private static int port = 8000;

    static {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("服务器已启动...");
        } catch (IOException e) {
            logger.error("serverSocket异常：" + e.getMessage());
        }
        executorService = Executors.newFixedThreadPool(POOL_SIZE);
        instance = new EchoServer();
    }

    public static EchoServer getInstance() {
        return instance;
    }

    private void service() {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                executorService.execute(new SocketHandler(socket));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
