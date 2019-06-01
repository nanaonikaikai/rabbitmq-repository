package com.nepu.rabbitmq_api.qucksart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author nanaOni
 * @Date 2019/5/31 9:44
 * @Version 1.0
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1、创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.145");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2、通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3、通过connection创建一个Channel
        Channel channel = connection.createChannel();

        // 4、声明一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName,true,false,false,null);

        // 5、创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 6、设置Channel
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true) {
            // 7、获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
//            Envelope envelope = delivery.getEnvelope();
//            envelope.getDeliveryTag();
            System.out.println(msg);
        }


//        // 8、关闭相关的连接
//        channel.close();
//        connection.close();
    }
}
