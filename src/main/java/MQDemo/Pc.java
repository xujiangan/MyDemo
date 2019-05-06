package MQDemo;

import com.rabbitmq.client.*;

public class Pc {

    private static final String EXCHANGE_NAME = "amp.topic";
    private static final String[] routingKeys = {"adtec.laoliu", "ali.xiaowang", "tencent.laozhang", "adtec.xiaohu"};

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // factory.setConnectionTimeout(200);
        factory.setAutomaticRecoveryEnabled(true);//设置连接断开自动重连
        factory.setNetworkRecoveryInterval(10);//每隔10S重连一次
        factory.setHandshakeTimeout(10000);//设置连接握手超时时间，防止立马报连接超时

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        // confirm模式

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = getMessage("hello");

        String key = "receive.device.status";

        //分别推送"adtec.laoliu","ali.xiaowang", "tencent.laozhang", "adtec.xiaohu"消息

        String msg = message + "' from " + key;
        channel.basicPublish(EXCHANGE_NAME, key, null, msg.getBytes());
        System.out.println(" [x] Sent '" + key + "':'" + message + "'");

        channel.close();
        connection.close();

    }

    private static String getMessage(String strings) {

        return "info: Hello World!";
    }

}
