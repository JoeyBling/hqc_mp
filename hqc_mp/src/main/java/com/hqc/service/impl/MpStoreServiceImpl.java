package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.hqc.dao.MpStoreDao;
import com.hqc.entity.MpStoreEntity;
import com.hqc.service.MpStoreService;
import com.hqc.util.Constant;

/**
 * 微信门店服务实现类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Service
@CacheConfig(cacheNames = Constant.REDIS_CACHE_NAME)
public class MpStoreServiceImpl implements MpStoreService {

	@Resource
	private MpStoreDao dao;

	@Override
	public int save(MpStoreEntity entity) {
		return dao.insert(entity);
	}

	@Override
	public List<MpStoreEntity> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	@Override
	public MpStoreEntity queryObject(String poiId) {
		return dao.queryObject(poiId);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal(map);
	}

	@Override
	public int updateByPrimaryKey(MpStoreEntity entity) {
		return dao.updateByPrimaryKey(entity);
	}

	@Override
	public int deleteAll() {
		return dao.deleteAll();
	}

	@Override
	public int delete(String poiId) {
		return dao.delete(poiId);
	}

	@Override
	public int deleteByPoiId(String[] split) {
		return dao.deleteByPoiId(split);
	}

}
