package demo;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.embedded.RedisServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EmbeddedRedisServerBeanIntegrationTests {
	@Autowired
	RedisTemplate<String, String> redis;

	@Autowired
	RedisConnectionFactory connectionFactory;

	@Test
	public void version() {
		Properties info = redis.execute(new RedisCallback<Properties>() {
			public Properties doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.info("Server");
			}
		});

		String redisVersion = (String) info.get("redis_version");
		System.out.println("======================");
		System.out.println(redisVersion);
		String[] parts = redisVersion.split("\\.");
		int majorVersion = Integer.parseInt(parts[0]);
		int minorVersion = Integer.parseInt(parts[1]);

		boolean is3xOrLarger = majorVersion >= 3;
		boolean is2Dot8OrLarger = is3xOrLarger || (majorVersion >= 2 && minorVersion >= 8);

		assertTrue("Expected 2.8.x or larger. Got "+ redisVersion, is2Dot8OrLarger);
	}

	@Configuration
	static class Config {

		@Bean
		public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
			RedisTemplate<String, String> redis = new RedisTemplate<String, String>();
			redis.setConnectionFactory(connectionFactory);
			return redis;
		}

		@Bean
		public JedisConnectionFactory connectionFactory() {
			JedisConnectionFactory connection = new JedisConnectionFactory();
			return connection;
		}
	}

}
