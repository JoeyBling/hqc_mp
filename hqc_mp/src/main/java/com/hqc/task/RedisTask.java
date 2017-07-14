package com.hqc.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.util.RedisUtil;

/**
 * Redis任务
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月26日
 * 
 */
@Component
public class RedisTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected RedisUtil redisUtil;

	/**
	 * 每日凌晨0点清空Redis缓存
	 * 
	 * @throws Exception
	 */
	@Transactional
	// @Scheduled(cron = "0 0 1 * * ? ")
	public void autoTask() throws Exception {
		redisUtil.clear();
		logger.info("清空所有Redis缓存成功");
	}
}
