package com.hqc.service.impl;

import com.hqc.dao.MpUpdateIntegeralTaskDao;
import com.hqc.service.MpUpdateIntegralTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("autoUpdateIntegraltask")
public class MpUpdateIntegralTaskServiceImpl implements
        MpUpdateIntegralTaskService {
    @Resource
    private MpUpdateIntegeralTaskDao integralTaskDao;

    /**
     * 修改积分
     */
    @Override
    public List<Map<String, Object>> autoUpdateIntegral() {
        // TODO Auto-generated method stub
        return integralTaskDao.autoUpdateIntegral();
    }

}
