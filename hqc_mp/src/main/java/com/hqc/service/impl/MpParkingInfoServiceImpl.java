package com.hqc.service.impl;

import com.hqc.dao.MpParkingInfoDao;
import com.hqc.entity.MpParkingcChargeEntity;
import com.hqc.service.MpParkingInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MpParkingInfoServiceImpl implements MpParkingInfoService {

    @Resource
    private MpParkingInfoDao dao;

    @Override
    public List<MpParkingcChargeEntity> queryList(Map<String, Object> map) {
        return dao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return dao.queryTotal(map);
    }

}
