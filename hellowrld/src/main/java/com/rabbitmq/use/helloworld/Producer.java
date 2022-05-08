package com.rabbitmq.use.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
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

            //开启confirm监听模式
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //第二个参数代表接收的数据是否为批量接收，一般我们用不到
                    System.out.println("消息已被broker接收，Tag："+1);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("消息已被broker拒收，Tag："+1);
                }
            });

            String message = "first column";
            int i=0;
            while (true){
                i++;
                channel.basicPublish("",RabbitConstant.QUEUE_HELLOWORLD,null,message.getBytes());
                if (i>Integer.MAX_VALUE){
                    break;
                }
            }

            //开启监听的时候，不能关掉。
//            channel.close();
//            connection.close();


            System.out.println("====发送成功=====");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }


    }
}
