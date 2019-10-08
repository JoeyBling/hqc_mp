package com.hqc.dao;

import com.hqc.entity.MpMemberEntity;

import java.util.List;
import java.util.Map;

/**
 * 修改会员积分定时任务
 *
 * @author Administrator
 */
public interface MpUpdateIntegeralTaskDao extends BaseDao<MpMemberEntity> {
    /**
     * 修改积分
     */
    public List<Map<String, Object>> autoUpdateIntegral();


}
