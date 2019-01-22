package com.tpadsz.after.socket;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    private Logger logger = Logger.getLogger(this.getClass());
    private static ServerSocket serverSocket = null;
    private ExecutorService executorService;  //线程池
    private final int POOL_SIZE = 4;  //单个CPU时线程池中工作线程的数目

    public EchoServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        //Runtime的availableProcessors()方法返回当前系统的CPU的数目
        //系统的CPU越多，线程池中工作线程的数目也越多
        int cpu_num = Runtime.getRuntime().availableProcessors();
        logger.info("系统的CPU的数目：" + cpu_num);
        //创建线程池
        executorService = Executors.newFixedThreadPool(cpu_num * POOL_SIZE);
    }

    public void service() {
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
