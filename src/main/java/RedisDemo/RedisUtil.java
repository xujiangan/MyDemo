package RedisDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;
}
