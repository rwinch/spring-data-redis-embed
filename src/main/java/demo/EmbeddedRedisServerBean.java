/*
 * Copyright 2011-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo;

import java.io.IOException;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.SocketUtils;

import redis.embedded.RedisServer;

/**
 *
 * @author Rob Winch
 * @since 1.6
 */
public final class EmbeddedRedisServerBean implements InitializingBean, DisposableBean {
	private final RedisServer redisServer;

	public EmbeddedRedisServerBean(RedisServer redisServer) {
		Assert.notNull(redisServer, "RedisServer may not be null");
		this.redisServer = redisServer;
	}

	public void afterPropertiesSet() throws Exception {
//		System.out.println("Starting on " + getPort());
//		redisServer.start();
	}

	public void destroy() throws Exception {
//		if(redisServer != null) {
//			System.out.println("Shutting down on " + getPort());
//			redisServer.stop();
//		}
	}

	public RedisServer getRedisServer() {
		return redisServer;
	}

	public int getPort() {
		return redisServer.ports().get(0);
	}

	public static EmbeddedRedisServerBean createWithAvailbeTcpPort() throws IOException {
		RedisServer redisServer = new RedisServer(SocketUtils.findAvailableTcpPort());
		return new EmbeddedRedisServerBean(redisServer);
	}
}