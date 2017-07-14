package com.hqc.util.wx;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hqc.entity.Mp520Entity;
import com.hqc.service.Mp520Service;
import com.hqc.util.DateUtils;

/**
 * 随机数帮助类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Component
public class Mp520Util {

	/** 随机数已生成最大的值 */
	public static Long start = 10000L;

	/** 随机数总数 */
	public static int count = 0;

	public static boolean init = true;

	public static Mp520Service mp520Service;

	@Resource
	public void setMp520Service(Mp520Service mp520Service) {
		Mp520Util.mp520Service = mp520Service;
	}

	/**
	 * 初始化
	 */
	private void init() {
		if (init) {
			Map<String, Object> map = new HashMap<String, Object>();
			int queryTotal = mp520Service.queryTotal(map);
			if (queryTotal > 0) {
				start += queryTotal;
				count += queryTotal;
			}
		}
	}

	/**
	 * 批量生成随机数
	 * 
	 * @param genNum
	 *            生成的数量
	 */
	public void createRandom(int genNum) {
		if (init) {
			init();
			init = false;
		}
		for (Long i = start; i < start + genNum; i++) {
			Mp520Entity entity = new Mp520Entity();
			entity.setCreateTime(DateUtils.getCurrentUnixTime());
			entity.setRandomNumber(i);
			mp520Service.save(entity);
			count++;
		}
		start += genNum;
	}

	/**
	 * 获得一个随机数
	 * 
	 * @param status
	 *            未使用0 男1 女2 配对成功3
	 * @return 东部华侨城520活动记录表
	 */
	public Mp520Entity getRandom(Integer status) {
		Mp520Entity entity = mp520Service.getRandomByOne(status);
		return entity;

	}
}
