package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpOrderRecordsEntity;

/**
 * 商品订单记录服务接口
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
public interface MpOrderRecordsService {

	/**
	 * 保存一个商品订单记录
	 * 
	 * @param entity
	 *            商品订单记录
	 * @return 影响行数
	 */
	int save(MpOrderRecordsEntity entity);

	/**
	 * 查询商品订单记录列表
	 * 
	 * @param map
	 * @return 商品订单记录列表
	 */
	List<MpOrderRecordsEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询商品订单记录
	 * 
	 * @param id
	 *            ID
	 * @return 商品订单记录
	 */
	MpOrderRecordsEntity queryObject(Long id);

	/**
	 * 查询商品订单记录总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 商品订单记录总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个商品订单记录
	 * 
	 * @param entity
	 *            商品订单记录
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpOrderRecordsEntity entity);

	/**
	 * 根据ID删除商品订单记录
	 * 
	 * @param id
	 *            ID
	 * @return 影响行数
	 */
	int delete(Long id);

}
