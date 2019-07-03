package middleware.redis.withspring;

import middleware.BaseJunit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisTest
 * description: 测试Spring集成Redis
 */
public class RedisTest extends BaseJunit4Test {
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void testSpringRedis() {
		redisTemplate.opsForValue().set("springRedis", "with spring");
		Object springRedis = redisTemplate.opsForValue().get("springRedis");
		System.out.println(springRedis);
	}
}
