package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpMemberEntity;


/**
 * 微信积分商城
 * @author Administrator
 *
 */
public interface IntegralMallWxDao  extends BaseDao<MpGoodsEntity>{
	/**
	 * 商城列表
	 */
	List<Map<String, Object>> getAllGoods();
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
