package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpGoodsExchangeRecordEntity;
import com.hqc.entity.MpMemberEntity;

public interface IntegralMallWxService {
	/**
	 * 商城列表
	 */
	List<Map<String, Object>> getAllGoods();
	/**
	 * 获得商品
	 */
	MpGoodsEntity queryObject(Long id);
	/**
	 * 查询客户商品兑换数量
	 */
	int getTotalGoodsExchangeByNow(Map<String, Object> map);
	/**
	 * 查询代金卷
	 */
	MpCashCouponEntity queryCashCouponEntity(Long id);
	/**
	 * 查询会员
	 */
	MpMemberEntity getMemberEntity(Long id);


}
