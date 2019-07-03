package middleware.redis.withspring;

import middleware.BaseJunit4Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisTest
 * description: 测试Spring集成Redis
 */
public class RedisTest extends BaseJunit4Test {
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void testSpringRedis() {
		// String
		redisTemplate.delete("springRedis");
		redisTemplate.opsForValue().set("springRedis", "with spring");
		Object springRedis = redisTemplate.opsForValue().get("springRedis");
		System.out.println(springRedis);

		// List
		redisTemplate.delete("myList");
		redisTemplate.opsForList().rightPush("myList", "a");
		redisTemplate.opsForList().rightPush("myList", "b");
		redisTemplate.opsForList().leftPush("myList", "c");
		List<String> myList = redisTemplate.opsForList().range("myList", 0, -1);
		for (String str : myList) {
			System.out.println(str);
		}

		// Set
		redisTemplate.delete("mySet");
		redisTemplate.opsForSet().add("mySet", "1");
		redisTemplate.opsForSet().add("mySet", "2");
		redisTemplate.opsForSet().add("mySet", "3");
		Set<String> mySet = redisTemplate.opsForSet().members("mySet");
		for (String str : mySet) {
			System.out.println(str);
		}

		// Hash
		redisTemplate.delete("myHash");
		redisTemplate.opsForHash().put("myHash", "BJ", "北京");
		redisTemplate.opsForHash().put("myHash", "NJ", "南京");
		redisTemplate.opsForHash().put("myHash", "SH", "上海");
		Map<String, String> myHash = redisTemplate.opsForHash().entries("myHash");
		for (Map.Entry entry : myHash.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}


	}
}
