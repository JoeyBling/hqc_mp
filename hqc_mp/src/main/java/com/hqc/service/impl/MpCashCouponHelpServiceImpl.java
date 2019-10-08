package com.hqc.service.impl;

import com.hqc.dao.MpCashCouponHelpDao;
import com.hqc.entity.MpCashCouponHelpEntity;
import com.hqc.service.MpCashCouponHelpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 个人优惠卷显示
 *
 * @author cxw
 * @date 2017年6月12日
 */
@Service("mpCashCouponHelpServiceImpl")
public class MpCashCouponHelpServiceImpl implements MpCashCouponHelpService {
    @Resource
    private MpCashCouponHelpDao cashCouponHelpDao;

    @Override
    public List<MpCashCouponHelpEntity> queryList(Map<String, Object> map) {
        return this.cashCouponHelpDao.queryList(map);
    }

}
