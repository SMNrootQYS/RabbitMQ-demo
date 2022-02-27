package com.example.smnpproject.controller;

import com.rabbitmq.client.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author songmeining
 * @description 二、work_queue队列模型(轮询)
 * @date 2021-11-17 16:10
 */
@RestController
@RequestMapping("/workQueue")
public class WorkQueueController {

     /**
      * 队列名
      * */
     String QUEUE_NAME = "queue_work";


    @GetMapping("/sendWorkQueue")
    public String sendWorkQueue() throws IOException, TimeoutException {

        ConnectionFactory  factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String messageBody = "This is message from produce";
        for (int i = 0; i < 50; i++) {
            channel.basicPublish("", QUEUE_NAME, null, (messageBody + "-->" + i).getBytes(StandardCharsets.UTF_8));
        }
        channel.close();
        connection.close();
        return "ok";
    }

    @GetMapping("/revWorkQueueOne")
    public String revWorkQueueOne() throws IOException, TimeoutException {
        ConnectionFactory  factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("",false, false, false, null);
        boolean autoAck = true;

        channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                System.out.println("recive 1 :" + new String(body, "utf-8"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


      return "ok";
    }

    @GetMapping("/revWorkQueueTwo")
    public String revWorkQueueTwo() throws IOException, TimeoutException {
        ConnectionFactory  factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("",false, false, false, null);
        boolean autoAck = true;

        channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                System.out.println("recive 2 :" + new String(body, "utf-8"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return "ok";
    }
}
