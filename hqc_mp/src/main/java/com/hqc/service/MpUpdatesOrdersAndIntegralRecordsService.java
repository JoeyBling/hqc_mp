package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpOrderRecordsEntity;

/**
 * 修改订单表与兑换表,门表订单使用状态统一service
 * @author Administrator
 *
 */
public interface MpUpdatesOrdersAndIntegralRecordsService {
	/**
	 * 查询订单
	 */
	public List<Map<String, Object>> queryOrderyList(Map<String, Object> map);
	/**
	 * 查询总数
	 */
	public int queryOrderTotal(Map<String, Object> map);
	/**
	 * 修改订单表状态
	 */
	public void updateOrderStatus(MpOrderRecordsEntity orderEntity);
	/**
	 * 修改兑换记录状态
	 */
	public void updateExchangeRecords(Map<String, Object> map);
}
