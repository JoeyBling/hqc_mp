package com.hqc.service;

import com.hqc.entity.MpCashCouponHelpEntity;

import java.util.List;
import java.util.Map;

/**
 * 个人优惠卷显示
 *
 * @author cxw
 * @date 2017年6月12日
 */
public interface MpCashCouponHelpService {

    /**
     * 查询（我的优惠卷）
     *
     * @param map
     * @return
     */
    List<MpCashCouponHelpEntity> queryList(Map<String, Object> map);
}
