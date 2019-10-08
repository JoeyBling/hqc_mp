package com.hqc.service.impl;

import com.hqc.dao.GoodsExchangeDao;
import com.hqc.service.GoodsExchangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("goodsExchangeService")
public class GoodsExchangeServiceImpl implements GoodsExchangeService {

    @Resource
    private GoodsExchangeDao goodsExchangeDao;

    /**
     * 列表
     */
    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return goodsExchangeDao.queryList1(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return goodsExchangeDao.queryTotal(map);
    }

    /**
     * 删除
     */
    @Override
    public void deleteBatch(long[] ids) {
        goodsExchangeDao.deleteBatch(ids);
    }

    @Override
    public List<Map<String, Object>> queryByMemberId(Map<String, Object> map) {
        return goodsExchangeDao.queryByMemberId(map);
    }

    @Override
    public int recordTotal(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return goodsExchangeDao.recordTotal(map);
    }

}
