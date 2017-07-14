package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpOrderRecordsDao;
import com.hqc.entity.MpOrderRecordsEntity;
import com.hqc.service.MpOrderRecordsService;

/**
 * 商品订单记录服务实现类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月10日
 * 
 */
@Service
public class MpOrderRecordsServiceImpl implements MpOrderRecordsService {

	@Resource
	private MpOrderRecordsDao dao;

	@Override
	public int save(MpOrderRecordsEntity entity) {
		return dao.insertSelective(entity);
	}

	@Override
	public List<MpOrderRecordsEntity> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	@Override
	public MpOrderRecordsEntity queryObject(Long id) {
		return dao.queryObject(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal(map);
	}

	@Override
	public int updateByPrimaryKey(MpOrderRecordsEntity entity) {
		return dao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(Long id) {
		return dao.deleteByPrimaryKey(id);
	}

}
