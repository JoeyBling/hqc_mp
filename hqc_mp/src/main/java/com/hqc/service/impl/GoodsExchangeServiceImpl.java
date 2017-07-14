package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.GoodsExchangeDao;
import com.hqc.service.GoodsExchangeService;

@Service("goodsExchangeService")
public class GoodsExchangeServiceImpl implements GoodsExchangeService {

	@Resource
	private GoodsExchangeDao goodsExchangeDao;

	/**
	 * 列表
	 */
	public List<Map<String, Object>> queryList(Map<String, Object> map) {
		return goodsExchangeDao.queryList1(map);
	}

	public int queryTotal(Map<String, Object> map) {
		return goodsExchangeDao.queryTotal(map);
	}

	/**
	 * 删除
	 */
	public void deleteBatch(long[] ids) {
		goodsExchangeDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String, Object>> queryByMemberId(Map<String, Object> map) {
		return goodsExchangeDao.queryByMemberId(map);
	}

	@Override
	public int recordTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsExchangeDao.recordTotal(map);
	}

}
