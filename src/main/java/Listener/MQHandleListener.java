package Listener;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * MQ 监听生产者
 */
public class MQHandleListener implements MessageListener {

    public void onMessage(Message message) {
        try {
            // 通过json来转换（可发送消息类型map）
            String body = new String(message.getBody(), "UTF-8");
            System.out.println("监听到的消息是：" + body);
        } catch (Exception ex) {

        }
    }
}
