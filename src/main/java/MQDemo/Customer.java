package MQDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import javax.print.DocFlavor;
import javax.xml.parsers.FactoryConfigurationError;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ：xujan
 * @date ：2019-3-28 14:38:53
 * @description： MQ 消费者 单机器
 * @modified By：
 * @version:
 */
public class Customer {
    // 队列名称
    private final static String QUEUE_NAME = "rabbitMQ.test4";

    public static void main(String[] args) {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置MQ信息
        connectionFactory.setHost("localhost");

        try {
            System.out.println("开始消费");
            long startTime = System.currentTimeMillis();
            // 创建连接
            Connection connection = connectionFactory.newConnection();
            // 创建通道
            Channel channel = connection.createChannel();
            if(connection == null || channel == null){
                System.out.println("连接或通道不存在！");
                return;
            }
            // 声明需要关注的队列
            /*
            * @param1 队列名称
            * @param2 是否持久化 （true 服务器重启时生存 队列中存在则会抛406错误信息）
            * @param3 是否独占队列（使用私有队列，断开后删除）
            * @param4 所有消费者断开客户端后是否自动删除
            * @param5
            * */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Customer waiting receive message!");
            // 取服务器频道中的消息
            Consumer consumer = new DefaultConsumer(channel) {
                /*
                * @param1 envelope 存生产者相关信息（exchange routingkey）
                * @param2 body     消费消息
                * */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Customer Received '" + message + "'");
                }
            };
            // 自动回复队列应答
            /*
            * @param2 autoACK (true 使用消息回复机制)
            * */
            channel.basicConsume(QUEUE_NAME, true, consumer);
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("消费结束，耗时：" + endTime + "秒");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
