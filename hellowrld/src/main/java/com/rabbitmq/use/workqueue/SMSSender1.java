package com.rabbitmq.use.workqueue;

import com.rabbitmq.client.*;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SMSSender1 {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS,false,false,false,null);

        channel.basicConsume(RabbitConstant.QUEUE_SMS,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                System.out.println("SMSSender1-短信发送成功："+jsonSMS);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });
    }
}
