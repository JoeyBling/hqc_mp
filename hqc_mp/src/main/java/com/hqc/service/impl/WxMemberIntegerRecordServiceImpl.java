package com.hqc.service.impl;

import com.hqc.dao.WxMemberIntegerRecordDao;
import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.service.WxMemberIntegerRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("wxMemberIntegerRecordService")
public class WxMemberIntegerRecordServiceImpl implements
        WxMemberIntegerRecordService {
    @Resource
    private WxMemberIntegerRecordDao integeralRecordDao;

    @Override
    public List<MpIntegralRecordEntity> queryList(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return integeralRecordDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return integeralRecordDao.queryTotal(map);
    }

}
