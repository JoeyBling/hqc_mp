package com.hqc.service;

import java.text.ParseException;

import com.hqc.entity.MpCashCouponEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.entity.MpGoodsExchangeRecordEntity;
import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpOrderRecordsEntity;

/**
 * 获得消费积分
 * @author Administrator
 *
 */
public interface MemberGoodsIntegralService {
	/**
	 * 判断是否特别日
	 */
	boolean specialDay(MpMemberEntity entity)throws Exception;
	/**
	 *消费 修改积分
	 * @param entity 会员
	 * @param money 消费金额
	 * @throws Exception
	 */
	void UpdateMemberIntegral(MpMemberEntity tempEntity,Double money)throws Exception;
	/**
	 * 修改积分
	 * @param entity
	 * @param tempIntegral
	 * @param integralType
	 */
	//void addOfMiniuteMemberIntegral(MpMemberEntity entity,int tempIntegral,int integralType)throws Exception;
	/**
	 * 添加会员积分记录 
	 * @param entity 
	 * @param tempIntegral 积分
	 * @param integralType 积分类型 1 进账 2 出账
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	 void addMemberIntegralRecord(MpMemberEntity entity,int tempIntegral,int integralType) throws Exception;
	 /**
	  * 添加积分商品兑换记录
	  */
	 void insert(MpGoodsExchangeRecordEntity entity);
	 /**
	  * 增加积分
	  */
	 void addMemberIntegral(MpMemberEntity entity,int tempIntegral);
	 /**
	  * 积分相减
	  */
	 void miniuteMemberIntegral(MpMemberEntity entity,int tempIntegral);
	 /**
	  * 修改门票库存
	  */
	 void updateGoodsReporty(MpGoodsEntity entity);
	 /**
	  * 修改代金卷库存
	  */
	 void updateCashReporty(MpCashCouponEntity entity);
	 /**
	   * 添加进订单表
	   */
	 void insertMpOrderRecord(MpOrderRecordsEntity entity);
	 /**
	   * 查询订单号是否重复
	   */
	int getOrderNoByNewNo(String orderNo);
	

}
