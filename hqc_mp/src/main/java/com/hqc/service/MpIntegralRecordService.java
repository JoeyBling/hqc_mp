package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpIntegralRecordEntity;

public interface MpIntegralRecordService {
	/**
	 * 根据id做查询
	 * 
	 * @param id
	 * @return
	 */
	public MpIntegralRecordEntity querympIntegralRecordInfoId(long id);

	/**
	 * 添加
	 * 
	 * @param mpIntegralRecordEntity
	 */
	public void save(MpIntegralRecordEntity mpIntegralRecordEntity);

	/**
	 * 修改
	 * 
	 * @param mpIntegralRecordEntity
	 */
	public void update(MpIntegralRecordEntity mpIntegralRecordEntity);

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @return
	 */
	public List<MpIntegralRecordEntity> getAllList(Map<String, Object> map);

	/**
	 * 查询条数
	 * 
	 * @param map
	 * @return
	 */
	public int queryTotal(Map<String, Object> map);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void deleteBatch(long[] id);

}
