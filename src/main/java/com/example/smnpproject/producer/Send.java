package com.example.smnpproject.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author songmeining
 * @description TODO
 * @date 2021-11-12 14:43
 */
@Slf4j
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        test();
    }

    public static void test() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world ---- test";
            // 第一个参数是交换机  为空则说明是匿名转发
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            log.info("Producer send: {}", message);
//            channel.close();
//            connection.close();
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
            log.error("错误---");
        }
    }
}
