import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Set;

@SpringBootTest
public class RedisTest {

    @Test
    public void testRedis() {
        Jedis jedis = new Jedis("localhost",6379);
        jedis.auth("12345");
        System.out.println(jedis.ping());
        System.out.println(jedis.get("k1"));
        Set<String> set = jedis.smembers("set");
        System.out.println("setstr="+Arrays.asList(set).toString());

    }
}
