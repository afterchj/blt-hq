package com.tpadsz.after.rabbit;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created by hongjian.chen on 2019/1/23.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:common.properties")
public class RabbitMqConfig {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private MessageConsumer messageConsumer;

    private String message = "topic.demo.message";
    private String messages = "topic.messages";

    @Value("${rabbitmq.queue}")
    private String LIGHT_QUEUE;
    @Value("${rabbitmq.host}")
    private String host;
    @Value("${rabbitmq.port}")
    private Integer port;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
//        factory.setUsername("guest");
//        factory.setPassword("guest");
//        factory.setVirtualHost("/");
        return factory;
    }

    @Bean
    public AmqpTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public TopicExchange directExchange() {
        return new TopicExchange("demoExchange");
    }

    @Bean
    public RabbitAdmin rabbitAdmin() throws Exception {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
        rabbitAdmin.declareExchange(directExchange());
        return rabbitAdmin;

    }

    @Bean
    public Queue helloQueue() {
        return new Queue("hello-queue");
    }

    @Bean
    public Queue tpadQueue() {
        return new Queue(LIGHT_QUEUE, true, false, false);
    }

    @Bean
    public Queue queueMessage() {
        return new Queue(message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(messages);
    }

    @Bean
    public Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.*.message");
    }

    @Bean
    public Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    @Bean
    public MessageListenerContainer messageListenerContainer() throws Exception {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setRabbitAdmin(rabbitAdmin());
        container.setQueues(helloQueue(), queueMessages());
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(messageConsumer);
        return container;
    }
}