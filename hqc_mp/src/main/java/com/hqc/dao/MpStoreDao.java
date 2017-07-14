package com.hqc.dao;

import com.hqc.entity.MpStoreEntity;

/**
 * 微信门店
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpStoreDao extends BaseDao<MpStoreEntity> {

	/**
	 * 删除所有门店信息(慎用)
	 * 
	 * @return 影响行数
	 */
	int deleteAll();

	/**
	 * 根据门店ID删除多个门店信息
	 * 
	 * @param split
	 *            多个门店ID
	 * @return 影响行数
	 */
	int deleteByPoiId(String[] split);

}
