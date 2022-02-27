package com.example.smnpproject.controller;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author songmeining
 * @description Fair_Work_Queue: 公平分发队列
 * @date 2021-11-17 17:40
 */
@RestController
@RequestMapping("/fairWorkQueue")
public class FairWorkQueueController {

    /**
     * 队列名
     * */
    String QUEUE_NAME = "queue_work_fair";


    @GetMapping("/sendWorkQueue")
    public String sendWorkQueue() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(0, 1 , false);
        String messageBody = "This is message from produce";
        for (int i = 0; i < 50; i++) {
            channel.basicPublish("", QUEUE_NAME, null, (messageBody + "-->" + i).getBytes(StandardCharsets.UTF_8));
        }
//        channel.close();
//        connection.close();
        return "ok";
    }

    @GetMapping("/revWorkQueueOne")
    public String revWorkQueueOne() throws IOException, TimeoutException {
        ConnectionFactory  factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("",false, false, false, null);
        // 一次仅接受一条未经确认的消息，
        channel.basicQos(1);

        // 接收到消息后的回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received 1'" + message + "'");
            // 返回确认消息给rabbitmq
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        boolean autoAck = false;
        // 监听队列，每当队列中接收到新消息后会触发回调函数
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });

        return "ok";
    }

    @GetMapping("/revWorkQueueTwo")
    public String revWorkQueueTwo() throws IOException, TimeoutException {
        ConnectionFactory  factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("",false, false, false, null);
        // 一次仅接受一条未经确认的消息，
        channel.basicQos(1);

        // 接收到消息后的回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received 2'" + message + "'");
            // 返回确认消息给rabbitmq
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        boolean autoAck = false;
        // 监听队列，每当队列中接收到新消息后会触发回调函数
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
        return "ok";
    }
}
