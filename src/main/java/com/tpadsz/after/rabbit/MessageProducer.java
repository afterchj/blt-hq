package com.tpadsz.after.rabbit;

import com.tpadsz.after.utils.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/1/23.
 */

@Component
public class MessageProducer {

    private Logger logger = Logger.getLogger(MessageProducer.class);
    private static final String ROUTING_KEY = PropertiesUtils.getValue("rabbitmq.key");

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(int i) {
        String context = "hi, i am come from spring_exchange message all ";
        rabbitTemplate.convertAndSend("spring_exchange", "topic.messages", context + i);
    }

    public void send1(int i) {
        String context = "hi, i am message * ";
        rabbitTemplate.convertAndSend("spring_exchange", "topic.test.message", context + i);
    }

    public void sendMsg(String msg) {
        rabbitTemplate.convertAndSend(ROUTING_KEY, msg);
    }
}
