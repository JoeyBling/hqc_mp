package com.hqc.service;

import com.hqc.entity.MpIntegralRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 微信端我的积分
 *
 * @author Administrator
 */
public interface WxMemberIntegerRecordService {
    /**
     * 查询数据
     *
     * @param map
     * @return
     */
    public List<MpIntegralRecordEntity> queryList(Map<String, Object> map);

    public int queryTotal(Map<String, Object> map);
}
