package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpVipLevelDao;
import com.hqc.entity.MpVipLevelEntity;
import com.hqc.service.MpVipLevelService;

@Service("MpVipLevelService")
public class MpVipLevelServiceImpl implements MpVipLevelService {
	@Resource
	private MpVipLevelDao mpVipLevelDao;
	
	@Override
	public MpVipLevelEntity queryMpVipLevelInfoId(long id) {
		return mpVipLevelDao.queryMpVipLevelInfoId(id);
	}

	@Override
	public void update(MpVipLevelEntity mpVipLevelEntity) {
		mpVipLevelDao.update(mpVipLevelEntity);		
	}

	@Override
	public void save(MpVipLevelEntity mpVipLevelEntity) {
		mpVipLevelDao.save(mpVipLevelEntity);		
	}

	@Override
	public void deleteBatch(long[] id) {
		mpVipLevelDao.deleteBatch(id);		
	}

	@Override
	public List<MpVipLevelEntity> queryMpVipLevelName(String vipName) {
		return mpVipLevelDao.queryMpVipLevelName(vipName);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return mpVipLevelDao.queryTotal(map);
	}

	@Override
	public List<MpVipLevelEntity> queryAllList(Map<String, Object> map) {
		return mpVipLevelDao.queryAllList(map);
	}

	@Override
	public long queryMaxIntegral() {
		return mpVipLevelDao.queryMaxIntegral();
	}

	@Override
	public MpVipLevelEntity getMin() {
		return mpVipLevelDao.getMinOrMax("min_integral");
	}

	@Override
	public MpVipLevelEntity getMax() {
		return mpVipLevelDao.getMinOrMax("max_integral");
	}

	@Override
	public MpVipLevelEntity queryLevel(Long integral) {
		// TODO Auto-generated method stub
		return mpVipLevelDao.queryLevel(integral);
	}

	@Override
	public MpVipLevelEntity queryVipLevel(String vipName) {
		// TODO Auto-generated method stub
		return mpVipLevelDao.queryVipLevel(vipName);
	}

}
