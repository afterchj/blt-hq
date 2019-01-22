package com.tpadsz.after.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;

/**
 * Created by hongjian.chen on 2018/3/1.
 */
public class BLTService {

//    private Logger logger = Logger.getLogger(BLTService.class);
//    @Autowired
//    private UserService userService;
//
//    private PrintWriter getWriter(Socket socket) {
//        OutputStream socketOut;
//        try {
//            socketOut = socket.getOutputStream();
//            return new PrintWriter(socketOut, true);
//        } catch (IOException e) {
//            logger.error("获取输出流异常！" + e.getMessage());
//        }
//        return null;
//    }
//
//    private BufferedReader getReader(Socket socket) {
//        InputStream socketIn;
//        try {
//            socketIn = socket.getInputStream();
//            return new BufferedReader(new InputStreamReader(socketIn, "utf-8"));
//        } catch (IOException e) {
//            logger.error("获取输入流异常！" + e.getMessage());
//        }
//        return null;
//    }
//
//    public String echo(String msg) {
//        return "receive:" + msg;
//    }
//
//    public void run(Socket socket) {
//        System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
//        if (userService != null) {
//            System.out.println("userService=" + userService.getAll().size());
//        }
//        BufferedReader br = getReader(socket);
//        PrintWriter pw = getWriter(socket);
//        String msg;
//        try {
//            while ((msg = br.readLine()) != null) {
//                System.out.println(msg);
//                pw.println(echo(msg));
//                if (msg.equals("bye"))
//                    break;
//            }
//        } catch (IOException e) {
//            logger.error("status：" + e.getMessage());
//        } finally {
//            try {
//                if (socket != null) socket.close();
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//            }
//        }
//    }
}
