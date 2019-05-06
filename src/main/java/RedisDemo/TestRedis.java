package RedisDemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class TestRedis {

    public static void main(String[] args) {
        // 報錯 換個spring-core包版本
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-redis.xml");
        RedisTemplate redisTemplate = applicationContext.getBean(RedisTemplate.class);
        //设值
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForValue().set("key2","value2");
        //取值
        String value1 = (String) redisTemplate.opsForValue().get("key1");
        System.out.println(value1);
    }
}
