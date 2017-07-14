package com.hqc.util;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月22日
 * 
 */
@Component
public class RedisUtil {

	protected Logger logger = Logger.getLogger(getClass());
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private JedisPoolConfig jedisPoolConfig;

	/**
	 * 根据Key获取Value
	 * 
	 * @param key
	 *            Object
	 * @return Object
	 */
	public Object get(String key) {
		logger.info("Redis Get Key:" + key);
		final String keyf = key.toString();
		Object object = null;
		object = redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = keyf.getBytes();
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return JoeyUtil.toObject(value);
			}
		});
		return object;
	}

	/**
	 * 添加缓存
	 * 
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 * @param liveTime
	 *            生存时间(默认10分钟缓存期)86400一天
	 * @param always
	 *            是否永久生存
	 */
	public void put(Object key, Object value, Long liveTime,
			final boolean always) {
		logger.info("Put Key:" + key + " Value:" + value);
		final String keyf = key.toString();
		final Object valuef = value;
		// 默认10分钟缓存期
		final long liveTimes = liveTime == null ? 100 * 6 : liveTime;// 86400一天
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] keyb = keyf.getBytes();
				byte[] valueb = JoeyUtil.toByteArray(valuef);
				connection.set(keyb, valueb);
				if (!always && liveTimes > 0) {
					connection.expire(keyb, liveTimes);
				}
				return 1L;
			}
		});
	}

	/**
	 * 根据Key删除Value
	 * 
	 * @param key
	 *            Object
	 */
	public void evict(final String key) {
		logger.info("Del Key:" + key);
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.del(key.getBytes());
			}
		});
	}

	/**
	 * 清空所有缓存
	 */
	public void clear() {
		logger.info("Clear Key");
		redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

}
