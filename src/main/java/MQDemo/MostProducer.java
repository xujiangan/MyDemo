package MQDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author ：xujan
 * @date ：2019-3-28 14:38:53
 * @description： MQ 生产者 分布式 无交换机
 * @modified By：
 * @version:
 */
public class MostProducer {
    private static final String QUEUE_NAME = "FaceStar";
    private static final String CHANNEL_NAME = "channel_name_topic";
    private static final String[] routingKeys = {"a", "c", "b"};

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            // 1.fanout分发，所有消费者得到同样的队列信息
            //channel.exchangeDeclare(CHANNEL_NAME, "fanout");

           /* // 2.direct 路由交换机制 根据路由匹配
            channel.exchangeDeclare(CHANNEL_NAME, "direct");
            // 发送信息
            for (String routingKey : routingKeys) {
                String sendMessage = "test most send message" + routingKey;
                channel.basicPublish(CHANNEL_NAME, routingKey, null, sendMessage.getBytes());
                System.out.println("most producer send message " + sendMessage);
            }*/

            // 3.直接使用队列  排队获取
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // 4.topic 路由模糊匹配
            String[] routing = {"new.xujan.sea", "new.start.sea", "com.xujan.sea"};
            channel.exchangeDeclare(CHANNEL_NAME, "topic");
            for (String routingKey : routing) {
               /* // 声明队列
                channel.queueDeclare("queueName", true, true, false, null);
                // 绑定队列
                channel.queueBind("queueName", CHANNEL_NAME, routingKey);*/
                String sendMessage = "test most send message" + routingKey;
                // 持久化消息和队列(客户端 服务端同事设置，否则报错) dev=2
                AMQP.BasicProperties.Builder prop = new AMQP.BasicProperties.Builder();
                channel.basicPublish(CHANNEL_NAME, routingKey, prop.deliveryMode(2).build(), sendMessage.getBytes());
                System.out.println("most producer send message " + sendMessage);
            }

            // 分发消息
           /* for (int i = 0; i < 10; i++) {
                String sendMessage = "test most send message" + i;
                channel.basicPublish(CHANNEL_NAME, "", null, sendMessage.getBytes());

                //channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, sendMessage.getBytes());
                System.out.println("most producer send message " + sendMessage);
            }*/

            // 关闭连接
            if (connection != null) {
                channel.close();
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
