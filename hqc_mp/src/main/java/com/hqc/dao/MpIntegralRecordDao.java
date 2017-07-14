package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpGoodsExchangeRecordEntity;
import com.hqc.entity.MpIntegralRecordEntity;

public interface MpIntegralRecordDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
public MpIntegralRecordEntity querympIntegralRecordInfoId(long id);
/**
 * 
 * @param mpIntegralRecordEntity
 */
public void save(MpIntegralRecordEntity mpIntegralRecordEntity);
/**
 * 
 * @param mpIntegralRecordEntity
 */
public void	update(MpIntegralRecordEntity mpIntegralRecordEntity);

/**
 * 
 * @param map
 * @return
 */
public int queryTotal(Map<String, Object> map);
/**
 * 
 * @param id
 */
public void  deleteBatch(long[] id);
/**
 * 
 * @param map
 * @return
 */
public List<MpIntegralRecordEntity> getAllList(Map<String, Object> map);

}


