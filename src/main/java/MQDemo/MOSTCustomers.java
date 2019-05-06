package MQDemo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

/**
 * @author ：xujan
 * @date ：2019-3-28 14:38:53
 * @description： MQ 消费者2 分布式 无交换机
 * @modified By：
 * @version:
 */
public class MOSTCustomers {
    private static final String QUEUE_NAME = "task_queue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

            // 声明要获取消息队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println("mostproducer1 waiting for message!");

            // 设置每次取队列数量 1条
            channel.basicQos(1);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope
                        , AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        String message = new String(body, "UTF-8");
                        System.out.println("mostproducer1 recevice message " + message);

                        // TODO:休眠
                        Thread.sleep(3000);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        channel.abort();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Done");
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            //消息确认(autoack 为true,每次生产者发送消息就把内存消息删除，如果消费端出现程序异常退出，无法获取消费数据，可用手动回复代替)
            channel.basicConsume(QUEUE_NAME, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
