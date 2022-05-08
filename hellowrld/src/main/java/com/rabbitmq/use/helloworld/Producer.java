package com.rabbitmq.use.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.use.utils.RabbitConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    public static void main(String[] args) {
        //构建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机信息
        connectionFactory.setHost("192.168.125.60");

        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        //获取tcp长连接

        try {
            Connection connection = connectionFactory.newConnection();

            Channel channel = connection.createChannel();
            /**
             * first column:queue name
             * second column:是否持久化
             * third column：是否队列私有化：所有消费者都可以访问
             * fourth column:是否自动删除
             * fifth column:其他额外参数 null
             *
             */
            channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD,true,false,false,null);

            String message = "first column";
            int i=0;
            while (true){
                i++;
                channel.basicPublish("",RabbitConstant.QUEUE_HELLOWORLD,null,message.getBytes());
                if (i>Integer.MAX_VALUE){
                    break;
                }
            }
            channel.close();
            connection.close();
            System.out.println("====发送成功=====");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }
}
