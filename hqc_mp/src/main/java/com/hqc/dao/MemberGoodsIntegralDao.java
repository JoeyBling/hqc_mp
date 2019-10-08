package com.hqc.dao;

import com.hqc.entity.*;

import java.util.List;

/**
 * 积分公共方法
 *
 * @author Administrator
 */
public interface MemberGoodsIntegralDao extends BaseDao<MpGoodsExchangeRecordEntity> {
    /**
     * 查询特别日
     */
    int getSpecialDay(Long today);

    /**
     * 查找会员
     */
    MpMemberEntity findMpMemberEntity(MpMemberEntity entity);

    /**
     * 根据会员查找会员等级
     */
    MpVipLevelEntity getLevelByMember(MpMemberEntity entity);

    /**
     * 添加积分记录
     */
    void addIntegralRecord(MpIntegralRecordEntity recordEntity);

    /**
     * 修改会员积分
     */
    void updateMemberIntegral(MpMemberEntity integralEntity);

    /***
     * 查找会员等级
     */
    List<MpVipLevelEntity> getAllVipLevelList();

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
