package com.nepu.rabbitmq_api.api_message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author nanaOni
 * @Date 2019/5/31 9:44
 * @Version 1.0
 **/
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1、创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.145");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3、通过connection创建一个Channel
        Channel channel = connection.createChannel();

        Map<String,Object> headers = new HashMap<>();
        headers.put("my1",111);
        headers.put("my2",222);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentType("UTF-8")
                .expiration("10000")
                .headers(headers)
                .build();

        // 4、通过Channel发送数据
        for (int i=0;i<5;i++){
            String msg = "hello Rabbitmq";
            channel.basicPublish("","test001",properties,msg.getBytes());
        }

        // 5、关闭相关的连接
        channel.close();
        connection.close();
    }
}
