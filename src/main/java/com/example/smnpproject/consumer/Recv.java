package com.example.smnpproject.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author songmeining
 * @description TODO
 * @date 2021-11-12 14:44
 */
@Slf4j
public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
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
        channel.basicConsume(QUEUE_NAME, true, declared, consumer -> {});
    }
}
