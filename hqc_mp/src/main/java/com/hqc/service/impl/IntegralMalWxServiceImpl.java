package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.IntegralMallWxDao;
import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.service.IntegralMallWxService;
@Service("integralMallWxService")
public class IntegralMalWxServiceImpl implements IntegralMallWxService {
	@Resource
	private IntegralMallWxDao mallWxDao;
	
	/**
	 * 商城列表
	 */
	public List<Map<String, Object>> getAllGoods(){
		return mallWxDao.getAllGoods();
	}
	/**
	 * 获得商品
	 */
	public MpGoodsEntity queryObject(Long id){
		return mallWxDao.queryObject(id);
	}
	/**
	 * 查询客户商品兑换数量
	 */
	public int getTotalGoodsExchangeByNow(Map<String, Object> map){
		return mallWxDao.getTotalGoodsExchangeByNow(map);
	}
	/**
	 * 查询代金卷
	 */
	public MpCashCouponEntity queryCashCouponEntity(Long id){
		return mallWxDao.queryCashCouponEntity(id);
	}
	/**
	 * 查询会员
	 */
	public MpMemberEntity getMemberEntity(Long id){
		return mallWxDao.getMemberEntity(id);
	}

}
