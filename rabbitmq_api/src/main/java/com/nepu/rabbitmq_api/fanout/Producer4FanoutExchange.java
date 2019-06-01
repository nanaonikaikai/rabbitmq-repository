package com.nepu.rabbitmq_api.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer4DirectExchange
 * @Description TODO
 * @Author nanaOni
 * @Date 2019/5/31 14:03
 * @Version 1.0
 **/
public class Producer4FanoutExchange {
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

        // 4、声明
        String exchangeName = "test_fanout_exchange";
        // 不设置routingKey
        String routingKey = "";

        // 5、发送
        for (int i = 0 ; i < 20 ; i++) {
            String msg = "Hello World RabbitMQ 4 Fanout Exchange Message...";
            channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
        }


    }
}
