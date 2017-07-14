package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpIntegralRecordDao;
import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.service.MpIntegralRecordService;


@Service("MpIntegralRecordService")
public class MpIntegralRecordServiceImpl implements MpIntegralRecordService {
	@Resource
	private MpIntegralRecordDao mpIntegralRecordDao;

	@Override
	public MpIntegralRecordEntity querympIntegralRecordInfoId(long id) {
		return mpIntegralRecordDao.querympIntegralRecordInfoId(id);
	}

	@Override
	public void save(MpIntegralRecordEntity mpIntegralRecordEntity) {
		mpIntegralRecordDao.save(mpIntegralRecordEntity);

	}

	@Override
	public void update(MpIntegralRecordEntity mpIntegralRecordEntity) {
		mpIntegralRecordDao.update(mpIntegralRecordEntity);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return mpIntegralRecordDao.queryTotal(map);
	}

	@Override
	public void deleteBatch(long[] id) {
		mpIntegralRecordDao.deleteBatch(id);
	}

	@Override
	public List<MpIntegralRecordEntity> getAllList(Map<String, Object> map) {
		return mpIntegralRecordDao.getAllList(map);
	}
}
