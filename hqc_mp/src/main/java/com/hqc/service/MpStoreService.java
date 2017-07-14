package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpStoreEntity;

/**
 * 微信门店服务接口
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpStoreService {

	/**
	 * 保存一个微信门店
	 * 
	 * @param entity
	 *            微信门店
	 * @return 影响行数
	 */
	int save(MpStoreEntity entity);

	/**
	 * 查询微信门店列表
	 * 
	 * @param map
	 * @return 微信门店列表
	 */
	List<MpStoreEntity> queryList(Map<String, Object> map);

	/**
	 * 根据门店ID查询微信门店
	 * 
	 * @param poiId
	 *            微信门店ID
	 * @return 微信门店
	 */
	MpStoreEntity queryObject(String poiId);

	/**
	 * 查询微信门店总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 微信门店总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个微信门店
	 * 
	 * @param entity
	 *            活动记录
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpStoreEntity entity);

	/**
	 * 删除所有门店信息(慎用)
	 * 
	 * @return 影响行数
	 */
	int deleteAll();

	/**
	 * 根据门店ID删除门店信息
	 * 
	 * @param poiId
	 *            门店ID
	 * @return 影响行数
	 */
	int delete(String poiId);

	/**
	 * 根据门店ID删除多个门店信息
	 * 
	 * @param split
	 *            多个门店ID
	 * @return 影响行数
	 */
	int deleteByPoiId(String[] split);

}
