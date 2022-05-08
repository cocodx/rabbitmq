package com.rabbitmq.use.helloworld;

import com.rabbitmq.client.*;
import com.rabbitmq.use.utils.RabbitConstant;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

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

            //first column：队列名称
            //second column：是否自动确认收到消息
            //third column:要传入DefaultConsumer的实现类
            channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD,false,new Reciver(channel));
            System.out.println("====接收成功=====");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
class Reciver extends DefaultConsumer{
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    private Channel channel;
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body);
        System.out.println("消费者接收到的消息："+message);

        System.out.println("消息接收到的消息："+envelope.getDeliveryTag()); //消息的tagId
        //false代表只确认签收当前的消息 设置为true的时候则代表签收该消费者收到的所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(),false);
    }
}
