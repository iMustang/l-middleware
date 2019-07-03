package middleware.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * JedisDemo
 * description:Jedis操作Redis
 */
public class JedisDemo {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.121.130", 6379);
		jedis.set("test", "jedis test");
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
		JedisWithPool();
	}

	/**
	 * 使用连接池
	 */
	static void JedisWithPool() {
		JedisPool jedisPool = new JedisPool("192.168.121.130", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("pool", "jedis pool");
		String str = jedis.get("pool");
		System.out.println(str);
		jedis.close();
//		jedisPool.close();
	}
}
