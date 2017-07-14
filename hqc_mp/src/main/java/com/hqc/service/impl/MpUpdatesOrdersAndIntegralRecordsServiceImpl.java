package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpUpdatesOrderAndIntegralRecordsDao;
import com.hqc.entity.MpOrderRecordsEntity;
import com.hqc.service.MpUpdatesOrdersAndIntegralRecordsService;

/**
 * 修改订单表与兑换表,门表订单使用状态统一service
 * @author Administrator
 *
 */
@Service("updatesOrdersAndIntegralRecordsService")
public class MpUpdatesOrdersAndIntegralRecordsServiceImpl implements
		MpUpdatesOrdersAndIntegralRecordsService {
	@Resource
	private MpUpdatesOrderAndIntegralRecordsDao orderAndIntegralRecordsDao ;
	/**
	 * 查询订单
	 */
	@Override
	public List<Map<String, Object>> queryOrderyList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return orderAndIntegralRecordsDao.queryOrderyList(map);
	}
	/**
	 * 查询总数
	 */
	@Override
	public int queryOrderTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return orderAndIntegralRecordsDao.queryOrderTotal(map);
	}
	/**
	 * 修改订单表状态
	 */
	@Override
	public void updateOrderStatus(MpOrderRecordsEntity orderEntity) {
		// TODO Auto-generated method stub
		orderAndIntegralRecordsDao.updateOrderStatus(orderEntity);
	}
	/**
	 * 修改兑换记录状态
	 */
	@Override
	public void updateExchangeRecords(Map<String, Object> map) {
		// TODO Auto-generated method stub
		orderAndIntegralRecordsDao.updateExchangeRecords(map);
	}

}
