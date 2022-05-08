package com.rabbitmq.use.confirm;


import com.rabbitmq.client.*;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sina {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SINA, false, false, false, null);

        channel.queueBind(RabbitConstant.QUEUE_SINA, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "us.#");

        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_SINA , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("腾讯天气收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
