package com.hqc.dao;

import com.hqc.entity.MpGoodsCategoryEntity;

/**
 * 商品分类
 * 
 * @author Administrator
 * 
 */
public interface GoodsCategoryDao extends BaseDao<MpGoodsCategoryEntity> {
	/**
	 * 根据商品类型名称查找该类型是否存在
	 */
	int queryTotalByName(MpGoodsCategoryEntity data);

	/**
	 * 查询该类商品是否被使用
	 */
	int queryUsingCategory(Long id);

	/**
	 * 删除该类下所有商品
	 */
	void deleteGoodsByCategory(Long categoryId);

}
