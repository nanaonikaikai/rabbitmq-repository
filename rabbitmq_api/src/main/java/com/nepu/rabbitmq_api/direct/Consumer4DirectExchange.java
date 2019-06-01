package com.nepu.rabbitmq_api.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer4DirectExchange
 * @Description TODO
 * @Author nanaOni
 * @Date 2019/5/31 14:08
 * @Version 1.0
 **/
public class Consumer4DirectExchange {
    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        // 1、创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.145");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);

        // 2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3、通过connection创建一个Channel
        Channel channel = connection.createChannel();

        // 4、声明一个队列
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct";

        // 声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        // 声明一个队列
        channel.queueDeclare(queueName,false,false,false,null);
        // 建立一个绑定关系
        channel.queueBind(queueName,exchangeName,routingKey);

        // durable 是否持久化消息
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        // 参数：队列名称，是否自动ACK,Consumer
        channel.basicConsume(queueName,true,queueingConsumer);

        // 循环获取消息
        while (true) {
            // 7、获取消息，如果没有消息，这一步将会一直阻塞
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息：" + msg);
        }

    }
}
