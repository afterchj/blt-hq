package com.tpadsz.after.rabbit;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by hongjian.chen on 2019/1/23.
 */

@Component
public class MessageProducer {

    private Logger logger = Logger.getLogger(MessageProducer.class);

    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, i am message all";
        this.rabbitTemplate.convertAndSend("demoExchange", "topic.demo.message", context);
    }

    public void send1(int i) {
        String context = "hi, i am message * ";
        this.rabbitTemplate.convertAndSend("demoExchange", "topic.demo.message", context + i);
    }

    public void send2(int i) {
        String context = "hi, i am messages # ";
        this.rabbitTemplate.convertAndSend("demoExchange", "topic.messages", context + i);
    }

    public void sendMsg(int num) {
        rabbitTemplate.convertAndSend("tpad-blt-console-queue", "来自蓝牙灯的的问候 " + (num));
    }
}
