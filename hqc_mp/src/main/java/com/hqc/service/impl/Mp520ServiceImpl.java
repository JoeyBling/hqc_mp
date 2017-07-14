package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.hqc.dao.Mp520Dao;
import com.hqc.entity.Mp520Entity;
import com.hqc.service.Mp520Service;
import com.hqc.util.Constant;

/**
 * 东部华侨城520活动记录服务实现类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Service
@CacheConfig(cacheNames = Constant.REDIS_CACHE_NAME)
public class Mp520ServiceImpl implements Mp520Service {

	@Resource
	private Mp520Dao dao;

	@Override
	// @Cacheable(key = "'mp520_num_' + #mp520.randomNumber")
	// 防止并发访问
	public int save(Mp520Entity mp520) {
		return dao.insert(mp520);
	}

	@Override
	public List<Mp520Entity> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	@Override
	public Mp520Entity queryObject(Long id) {
		return dao.queryObject(id);
	}

	@Override
	public Mp520Entity getByNum(Integer num) {
		return dao.getByNum(num);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal(map);
	}

	@Override
	public Mp520Entity manByOpenId(String openId) {
		return dao.queryByOpenId(openId, 1);
	}

	@Override
	public Mp520Entity womanByOpenId(String openId) {
		return dao.queryByOpenId(openId, 2);
	}

	@Override
	public Mp520Entity queryByOpenIdAny(String openId) {
		return dao.queryByOpenIdAny(openId);
	}

	@Override
	public int queryMatchTotal(Integer match) {
		return dao.queryMatchTotal(match);
	}

	@Override
	public Mp520Entity getRandomByOne(Integer status) {
		return dao.getRandomByOne(status);
	}

	@Override
	public int updateByPrimaryKey(Mp520Entity mp520) {
		Mp520Entity queryByOpenIdAny = null;
		if (null != mp520.getManOpenId() && null == mp520.getWomanOpenId()) {
			queryByOpenIdAny = dao.queryByOpenIdAny(mp520.getManOpenId());
		} else if (null != mp520.getWomanOpenId() && null == mp520.getManOpenId()) {
			queryByOpenIdAny = dao.queryByOpenIdAny(mp520.getWomanOpenId());
		}
		if (null != queryByOpenIdAny) { // 数据库已存在记录
			return 1;
		}
		return dao.updateByPrimaryKey(mp520);
	}
}
