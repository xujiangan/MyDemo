package MQDemo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Spittle;

import java.util.Date;

public class SingleSpringProducer {

    public static void main(String[] args) {
        // 获取spring-rabbit配置路径
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-rabbit-producer.xml");

        // 获取rabbitTemplate
        AmqpTemplate template = (AmqpTemplate) applicationContext.getBean("rabbitTemplate");

        System.out.println("生产消息开始");
        long startTime = System.currentTimeMillis();
        // 生产消息
        for (int i = 0; i <= 10; i++) {
            /*
             *  this.convertAndSend(this.exchange, this.routingKey, object, (CorrelationData)null);
             * */
            template.convertAndSend(new Spittle((long) i, null, "test spring rabbit send message", new Date()));
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("生产消息结束,耗时：" + endTime);
    }
}
