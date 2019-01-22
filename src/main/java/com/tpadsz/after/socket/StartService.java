package com.tpadsz.after.socket;

import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;

/**
 * Created by hongjian.chen on 2019/1/22.
 */

@Service
public class StartService implements InitializingBean {

    private static Logger logger = Logger.getLogger(StartService.class);
    private static int port = 8000;
    private static ServerSocket serverSocket = null;

    static {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("服务器已启动...");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("serverSocket异常：" + e.getMessage());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(new StartRun()).start();
    }

    private class StartRun implements Runnable {
        @Override
        public void run() {
            Class clz;
            try {
                clz = Class.forName(PropertiesUtils.getValue("className"));
                Method method = clz.getMethod("service");
                Constructor constructor = clz.getConstructor(ServerSocket.class);
                Object object = constructor.newInstance(serverSocket);
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("socket启动异常：" + e.getMessage());
            }
        }
    }
}
