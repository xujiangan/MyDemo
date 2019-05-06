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
 * @description： MQ 消费者1 分布式 无交换机
 * @modified By：
 * @version:
 */
public class MOSTCustomer {
    private static final String QUEUE_NAME = "task_queue2";
    private static final String CHANNEL_NAME = "channel_name_topic";
    private static final String[] routingKeys = {"a", "c"};

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

           /* // 1.fanout 交换机制 所有消费者得到一样的消息
            channel.exchangeDeclare(CHANNEL_NAME, "fanout");
            // 随机产生一个队列名
            String queue = channel.queueDeclare().getQueue();
            // 绑定队列
            channel.queueBind(queue, CHANNEL_NAME, "");*/

            // 2.driect 交换机制 路由
           /* channel.exchangeDeclare(CHANNEL_NAME, "direct");
            String queueName = channel.queueDeclare().getQueue();
            // 根据路由关键字进行绑定
            for (String routingKey : routingKeys) {
                channel.queueBind(queueName, CHANNEL_NAME, routingKey);
            }*/


            // 3.声明要获取消息队列
            //channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //System.out.println("mostproducer1 waiting for message!");

            // 4.topic 交换机制 模糊匹配
            channel.exchangeDeclare(CHANNEL_NAME, "topic");
            //String queueName = channel.queueDeclare().getQueue();
            String[] routing = {"*.xujan.*"};
            // 根据路由关键字进行绑定TestQuq
            for (String routingKey : routing) {
                channel.queueBind("TestQuq1", CHANNEL_NAME, routingKey);
            }


            // 设置每次取队列数量 1条
            //channel.basicQos(1);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope
                        , AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        // 发送回调队列地址 correlationId 队列唯一值  replyTo 回调队列
                        /*AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                                .replyTo(QUEUE_NAME).build();*/
                        /*AMQP.BasicProperties basicProperties1 =new AMQP.BasicProperties().builder()
                                .correlationId(properties.getCorrelationId()).build();*/

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

            //消息确认(autoack 为true,每次生产者发送消息就把内存消息删除，如果消费端出现程序异常退出，无法获取消费数据，可用手动回复代替,
            // 手动回复，每当消费者收到并处理信息然后在通知生成者。最后从队列中删除这条信息。如果消费者异常退出，如果还有其他消费者，那么就会把队列中的消息发送给其他消费者，如果没有，等消费者启动时候再次发送)
            //channel.basicConsume(QUEUE_NAME, false, consumer);

            // 自动删除队列
            channel.basicConsume("TestQuq1", true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
