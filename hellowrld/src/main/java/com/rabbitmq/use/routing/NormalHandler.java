package com.rabbitmq.use.routing;

import com.rabbitmq.client.*;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 这个是要提前启动，进行监听exchange交换机，绑定队列就行了。
 */
public class NormalHandler {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_NORMAL_LOG,false,false,false,null);

        //用于将队列与交换机绑定
        channel.queueBind(RabbitConstant.QUEUE_NORMAL_LOG,RabbitConstant.EXCHANGE_LOG,RabbitConstant.ROUTING_INFO);
        channel.queueBind(RabbitConstant.QUEUE_NORMAL_LOG,RabbitConstant.EXCHANGE_LOG,RabbitConstant.ROUTING_WARNING);
        //消费者在接收到队列里的消息但没有返回确认结果之前,它不会将新的消息分发给它。
        channel.basicQos(1);
        channel.basicConsume(RabbitConstant.QUEUE_NORMAL_LOG,false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("普通日志handler收到log："+new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        });


    }
}
