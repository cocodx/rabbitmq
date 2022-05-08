package com.rabbitmq.use.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class WeatherBureau {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();
        String input = new Scanner(System.in).next();
        Channel channel = connection.createChannel();

        //第一个参数：交换机name,go to eat rice
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"",null,input.getBytes());


        channel.close();
        connection.close();
    }
}
