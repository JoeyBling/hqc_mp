package com.hqc.dao;

import com.hqc.entity.MpCashCouponEntity;

public interface CashCouponDao extends BaseDao<MpCashCouponEntity> {
	/**
	 * 查看代金券是否被使用
	 */
	public int queryUsingCash(Long id);
	
}
