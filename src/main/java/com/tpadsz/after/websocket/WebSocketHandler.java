package com.tpadsz.after.websocket;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by hongjian.chen on 2018/8/1.
 */
public class WebSocketHandler extends AbstractWebSocketHandler {

    private Logger logger = Logger.getLogger(this.getClass());
    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("连接已建立！");
        users.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
        users.remove(session);
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        users.remove(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        logger.info("收到消息：" + message.getPayload());
        JSONObject object = new JSONObject();
        object.put("result", message.getPayload());
//        TextMessage returnMessage = new TextMessage(session.getAttributes().get(Constants.SESSION_USERNAME.value())+" : "+message.getPayload());
        TextMessage returnMessage = new TextMessage(object.toJSONString());
        session.sendMessage(returnMessage);
//        sendToAllClients(returnMessage, session);
    }


    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            super.handleBinaryMessage(session, message);
        } catch (Exception e) {
            logger.error("handleBinaryMessage error:" + e.getMessage());
        }
    }

    private void sendToAllClients(TextMessage msg, WebSocketSession curSession) {
        try {
            for (WebSocketSession user : users) {
                if (user.isOpen()) {
//                    if (!user.getId().equals(curSession.getId())) {
                    user.sendMessage(msg);
//                    }
                } else {
                    users.remove(user.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
