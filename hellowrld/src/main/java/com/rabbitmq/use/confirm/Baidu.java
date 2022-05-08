package com.rabbitmq.use.confirm;


import com.rabbitmq.client.*;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Baidu {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_BAIDU, false, false, false, null);
        //queueBind用于将队列与交换机绑定
        //参数1：队列名 参数2：交互机名  参数三：路由key
        channel.queueBind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "*.*.*.20201127");
        //channel.queueUnbind(RabbitConstant.QUEUE_BAIDU, RabbitConstant.EXCHANGE_WEATHER_TOPIC, "*.*.*.20201127");
        //*.hebei.*.*
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_BAIDU , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("百度天气收到气象信息：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
