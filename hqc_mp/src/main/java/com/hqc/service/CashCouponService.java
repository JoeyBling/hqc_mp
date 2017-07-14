package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpCashCouponEntity;


/**
 * 代金卷
 * @author Administrator
 *
 */
public interface CashCouponService {
	/**
	 * 列表
	 */
	List<MpCashCouponEntity> queryList(Map<String, Object> map);
	int queryTotal(Map<String, Object> map);
	/**
	 * 增加
	 */
	void insert(MpCashCouponEntity entity);
	/**
	 * 修改
	 */
	void update(MpCashCouponEntity entity);
	/**
	 * 删除
	 */
	void delete(long[] ids);
	/**
	 * 查看代金券是否被使用
	 */
	int queryUsingCash(Long id);
	/**
	 * 查看
	 */ 
	MpCashCouponEntity queryObject(Long id);
	

}
