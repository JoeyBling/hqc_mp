package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.CashCouponDao;
import com.hqc.entity.MpCashCouponEntity;
import com.hqc.service.CashCouponService;

/**
 * 代金卷管理
 * @author Administrator
 *
 */
@Service("cashCouponService")
public class CashCouponServiceImpl implements CashCouponService {
    @Resource
    private CashCouponDao cashCouponDao;
    
    /**
	 * 列表
	 */
	public List<MpCashCouponEntity> queryList(Map<String, Object> map){
		return cashCouponDao.queryList(map);
	}
	public int queryTotal(Map<String, Object> map){
		return cashCouponDao.queryTotal(map);
	}
	/**
	 * 增加
	 */
	public void insert(MpCashCouponEntity entity){
		cashCouponDao.insert(entity);
	}
	/**
	 * 修改
	 */
	public void update(MpCashCouponEntity entity){
		cashCouponDao.update(entity);
	}
	/**
	 * 删除
	 */
	public void delete(long[] ids){
		cashCouponDao.deleteBatch(ids);
	}
	/**
	 * 查看代金券是否被使用
	 */
	public int queryUsingCash(Long id){
		return cashCouponDao.queryUsingCash(id);
	}
	/**
	 * 查看
	 */ 
	public MpCashCouponEntity queryObject(Long id){
		return cashCouponDao.queryObject(id);
	}
	
}
