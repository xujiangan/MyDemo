package MQDemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author ：xujan
 * @date ：2019-3-28 14:38:53
 * @description： MQ 生产者 单机器
 * @modified By：
 * @version:
 */
public class Producer {

    // 队列名称
    public final static String QUEUE_NAME = "rabbitMQ.test4";

    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置MQ信息
        connectionFactory.setHost("localhost");
//       connectionFactory.setUsername("");
//       connectionFactory.setPassword("");
//       connectionFactory.setPort(2088);
        try {
            System.out.println("MQ开始生产");
            long startTime = System.currentTimeMillis();
            // 创建连接(池化技术)
            Connection connection = connectionFactory.newConnection();
            // 创建通道
            Channel channel = connection.createChannel();
            // 声明队列
            //channel.queueDeclare(QUEUE_NAME, false, false, true, null);
            // 发送消息到队列
            String sendMessage = "test send rabbitmq message!";
            /*
            * @Param1 exchange   交换机名称
            * @param2 routingKey 唯一
            * @param3
            * @param4 消息主体
            * */
            channel.basicPublish("", QUEUE_NAME, null, sendMessage.getBytes("UTF-8"));
            System.out.println("Producer send  " + sendMessage);
            // 关闭连接通道
            channel.close();
            connection.close();
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("MQ结束生产，耗时:" + endTime + "秒");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
