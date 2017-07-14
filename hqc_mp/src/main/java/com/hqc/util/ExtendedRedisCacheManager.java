package com.hqc.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.core.RedisOperations;

import java.util.Collection;
import java.util.Collections;

/**
 * CacheManager backed by a Simple Spring Redis (SSR)
 * {@link org.springframework.data.redis.cache.RedisCache}. Spring Cache and
 * CacheManager doesn't support configuring expiration time per method (there is
 * no dedicated parameter in cache annotation to pass expiration time). This
 * extension of {@link RedisCacheManager} overcomes this limitation and allow to
 * pass expiration time as a part of cache name. To define custom expiration on
 * method as a cache name use concatenation of specific cache name, separator
 * and expiration e.g.
 * <p/>
 * 
 * <pre>
 * public class UserDAO {
 * 
 * 	// cache name: userCache, expiration: 300s
 * 	&#064;Cacheable(&quot;userCache#300&quot;)
 * 	public User getUser(String name) {
 * 
 * 	}
 * }
 * </pre>
 * 
 * @author caiya
 * @since 16/3/18
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExtendedRedisCacheManager extends RedisCacheManager {

	private static final Logger logger = LoggerFactory
			.getLogger(ExtendedRedisCacheManager.class);

	private String defaultCacheName;

	private char separator = '#';

	public ExtendedRedisCacheManager(RedisOperations redisOperations) {
		this(redisOperations, Collections.<String> emptyList());
	}

	public ExtendedRedisCacheManager(RedisOperations redisOperations,
			Collection<String> cacheNames) {
		super(redisOperations, cacheNames);
	}

	@Override
	public Cache getCache(String name) {
		// try to get cache by name
		RedisCache cache = (RedisCache) super.getCache(name);
		if (cache != null) {
			return cache;
		}

		// there's no cache which has given name
		// find separator in cache name
		int index = name.lastIndexOf(getSeparator());
		if (index < 0) {
			return null;
		}

		// split name by the separator
		String cacheName = name.substring(0, index);
		if (StringUtils.isBlank(cacheName)) {
			cacheName = defaultCacheName;
		}
		cache = (RedisCache) super.getCache(cacheName);
		if (cache == null) {
			return null;
		}

		// get expiration from name
		Integer expiration = getExpiration(name, index);
		if (expiration == null || expiration < 0) {
			logger.warn("Default expiration time will be used for cache '{}' because cannot parse '{}', cacheName : "
					+ cacheName + ", name : " + name);
			return cache;
		}
		return new RedisCache(cacheName, (isUsePrefix() ? getCachePrefix()
				.prefix(cacheName) : null), getRedisOperations(), expiration);
	}

	public char getSeparator() {
		return separator;
	}

	/**
	 * Char that separates cache name and expiration time, default: #.
	 * 
	 * @param separator
	 */
	public void setSeparator(char separator) {
		this.separator = separator;
	}

	private Integer getExpiration(final String name, final int separatorIndex) {
		Integer expiration = null;
		String expirationAsString = name.substring(separatorIndex + 1);
		try {
			expiration = Integer.parseInt(expirationAsString);
		} catch (NumberFormatException ex) {
			logger.error(String.format(
					"Cannnot separate expiration time from cache: '%s'", name),
					ex);
		}

		return expiration;
	}

	@Override
	public void setUsePrefix(boolean usePrefix) {
		super.setUsePrefix(usePrefix);
	}

	@Override
	public void setCachePrefix(RedisCachePrefix cachePrefix) {
		super.setCachePrefix(cachePrefix);
	}

	public void setDefaultCacheName(String defaultCacheName) {
		this.defaultCacheName = defaultCacheName;
	}
}