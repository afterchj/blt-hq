package com.tpadsz.after;

import com.tpadsz.after.rabbit.MessageConsumer;
import com.tpadsz.after.rabbit.MessageProducer;
import com.tpadsz.after.rabbit.RabbitMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RabbitMqConfig.class)
public class SprintUnit {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageProducer producer;


    @Test
    public void testDirectConsumer() throws InterruptedException {
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(RabbitMqConfig.class);
//        MessageConsumer consumer = ctx.getBean(MessageConsumer.class);
//        System.out.println("beanName=" + consumer.getClass().getName());
        for (int i = 1; i < 101; i++) {
            if (i % 2 == 0) {
                producer.send();
            } else if (i % 3 == 0) {
                producer.send1(i);
            } else {
                producer.send2(i);
            }
            producer.sendMsg(i);
        }
        new Thread().sleep(5000);
    }
}
