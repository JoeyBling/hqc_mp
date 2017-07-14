package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.hqc.dao.MpWifiDao;
import com.hqc.entity.MpWifiEntity;
import com.hqc.service.MpWifiService;
import com.hqc.util.Constant;

/**
 * 微信WiFi设备服务实现类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Service
@CacheConfig(cacheNames = Constant.REDIS_CACHE_NAME)
public class MpWifiServiceImpl implements MpWifiService {

	@Resource
	private MpWifiDao dao;

	@Override
	public int save(MpWifiEntity entity) {
		return dao.insert(entity);
	}

	@Override
	public List<MpWifiEntity> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal(map);
	}

	@Override
	public int deleteByBssid(String[] bssid) {
		return dao.deleteByBssid(bssid);
	}

	@Override
	public int deleteAll() {
		return dao.deleteAll();
	}

}
