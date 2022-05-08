package com.rabbitmq.use.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

public class LogProduce {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();

        Channel channel = connection.createChannel();

        ArrayList<String> infoLogList = new ArrayList<>();
        ArrayList<String> warningLogList = new ArrayList<>();
        ArrayList<String> errorLogList = new ArrayList<>();

        infoLogList.add("中国湖南长沙20201127天气数据");
        infoLogList.add("中国湖北武汉20201127天气数据");

        warningLogList.add("中国湖南株洲20201128天气数据");
        warningLogList.add("美国加州洛杉矶20201127天气数据");

        errorLogList.add("中国河北石家庄20201128天气数据");
        errorLogList.add("中国湖北武汉20201128天气数据");
        errorLogList.add("中国河南郑州20201128天气数据");
        errorLogList.add("美国加州洛杉矶20201128天气数据");

        Iterator<String> iter1 = infoLogList.iterator();
        while (iter1.hasNext()){
            String log = iter1.next();
            //第一个参数 交换机的名字
            //第二个参数 路由key的名字
            channel.basicPublish(RabbitConstant.EXCHANGE_LOG,RabbitConstant.ROUTING_INFO,null,log.getBytes());
        }
        Iterator<String> iter2 = warningLogList.iterator();
        while (iter2.hasNext()){
            String log = iter2.next();
            //第一个参数 交换机的名字
            //第二个参数 路由key的名字
            channel.basicPublish(RabbitConstant.EXCHANGE_LOG,RabbitConstant.ROUTING_WARNING,null,log.getBytes());
        }
        Iterator<String> iter3 = errorLogList.iterator();
        while (iter3.hasNext()){
            String log = iter3.next();
            //第一个参数 交换机的名字
            //第二个参数 路由key的名字
            channel.basicPublish(RabbitConstant.EXCHANGE_LOG,RabbitConstant.ROUTING_ERROR,null,log.getBytes());
        }



        channel.close();
        connection.close();
    }
}
