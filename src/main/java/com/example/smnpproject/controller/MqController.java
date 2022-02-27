package com.example.smnpproject.controller;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author songmeining
 * @description 一、简单队列模式
 * @date 2021-11-17 11:07
 */
@Slf4j
@RequestMapping("/mq")
@RestController
public class MqController {

    private final static String QUEUE_NAME = "hello";

    @GetMapping("/produce")
    public String doProduce() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();  // 建立连接
            Channel channel = connection.createChannel()) { // 创建信道
            channel.queueDeclare(QUEUE_NAME, false, false, false, null); // 声明队列
            String message = "hello world ---- test";
            // 第一个参数是交换机  为空则说明是匿名转发
            // 发送消息
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            log.info("Producer send: {}", message);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
            log.error("错误---");
        }
        return "";
    }
    @GetMapping("/consumer")
    public String doConsumer() throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 定义消费者
        DeliverCallback declared = (consumer, deliverTag) -> {
            String message = new String(deliverTag.getBody(), "UTF-8");
            System.out.println("message===内容是" + message);
        };
        // 监听队列
        channel.basicConsume(QUEUE_NAME, true, declared, consumer -> {}); // 消费消息
        return connection.toString();
    }
}
