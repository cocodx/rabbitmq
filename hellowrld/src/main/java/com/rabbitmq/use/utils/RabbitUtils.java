package com.rabbitmq.use.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitUtils {

    public static Connection newConnection() throws IOException, TimeoutException {
        //构建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机信息
        connectionFactory.setHost("192.168.125.60");

        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}
