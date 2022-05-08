package com.rabbitmq.use.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.use.utils.RabbitConstant;
import com.rabbitmq.use.utils.RabbitUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class WeatherBureau {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitUtils.newConnection();
        Channel channel = connection.createChannel();

        Map area = new LinkedHashMap<String, String>();
        area.put("sina.hunan.changsha.20201127", "中国湖南长沙20201127天气数据");
        area.put("sina.hubei.wuhan.20201127", "中国湖北武汉20201127天气数据");
        area.put("sina.hunan.zhuzhou.20201127", "中国湖南株洲20201127天气数据");
        area.put("sina.cal", "美国加州洛杉矶20201127天气数据");

        area.put("baidu.hebei.shijiazhuang.20201128", "中国河北石家庄20201128天气数据");
        area.put("baidu.hubei.wuhan.20201128", "中国湖北武汉20201128天气数据");
        area.put("baidu.henan.zhengzhou.20201128", "中国河南郑州20201128天气数据");
        area.put("baidu.cal.lsj.20201128", "美国加州洛杉矶20201128天气数据");

        //第一个参数：交换机name,go to eat rice
        Iterator<Map.Entry<String, String>> itr = area.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, String> me = itr.next();
            //第一个参数交换机名字   第二个参数作为 消息的routing key
            channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER_TOPIC,me.getKey() , null , me.getValue().getBytes());

        }


        channel.close();
        connection.close();
    }
}
