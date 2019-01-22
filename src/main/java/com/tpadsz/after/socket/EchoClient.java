package com.tpadsz.after.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by hongjian.chen on 2018/22/1.
 */

public class EchoClient {
//    private String host = "122.112.229.195";
    private String host = "127.0.0.1";
    private int port = 8000;
    private Socket socket;

    public EchoClient() throws IOException {
        socket = new Socket(host, port);
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut, true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void talk() throws IOException {
        System.out.println("请输入内容：");
        try {
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg;
            while ((msg = localReader.readLine()) != null) {
                pw.println(msg);
                System.out.println(br.readLine());
                if (msg.equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        new EchoClient().talk();
    }
}
