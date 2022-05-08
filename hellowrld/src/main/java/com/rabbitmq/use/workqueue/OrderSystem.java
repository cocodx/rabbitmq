package com.rabbitmq.use.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送者
 */
public class OrderSystem {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(RabbitConstant.QUEUE_SMS,false,false,false,null);

        for (int i = 0; i <= Integer.MAX_VALUE; i++) {
            SMS sms = new SMS("乘客"+i,"1892255"+i,"您的车票已预订成功");
            String jsonSMSs = new Gson().toJson(sms);
            /**
             * first column ： exchange
             * second column ： queue name
             */
            channel.basicPublish("",RabbitConstant.QUEUE_SMS,null,jsonSMSs.getBytes());
        }
        System.out.println("发送数据成功");
        channel.close();
        connection.close();
    }
}
