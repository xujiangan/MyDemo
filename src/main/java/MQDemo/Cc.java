package MQDemo;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Cc {
    private static final String EXCHANGE_NAME = "amp.topic";
    private static final String[] bindingKeys = {"adtec.laoliu", "adtec.xiaowang", "adtec.xiaozhou", "adtec.xiaohu"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = "test";

        //binding那些bindingKey
        //可以单个单个设置，也可以批量设置
        String key = "receive.device.status";

        try{
          AMQP.Queue.BindOk ok =  channel.queueBind(queueName, EXCHANGE_NAME, "#.device.#");
            System.out.println(ok);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(" [*] Waiting for messages.");

        try {
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

            //ACK机制

            channel.basicConsume(queueName, true, consumer);
        }catch (Exception ex){
            ex.printStackTrace();
        }



    }

}
