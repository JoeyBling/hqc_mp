package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.WxMemberIntegerRecordDao;
import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.service.WxMemberIntegerRecordService;
@Service("wxMemberIntegerRecordService")
public class WxMemberIntegerRecordServiceImpl implements
		WxMemberIntegerRecordService {
	@Resource
	private WxMemberIntegerRecordDao integeralRecordDao;

	@Override
	public List<MpIntegralRecordEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return integeralRecordDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return integeralRecordDao.queryTotal(map);
	}

}
