package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpGoodsCategoryEntity;

/**
 * 商品分类
 * @author Administrator
 *
 */
public interface GoodsCategoryService {
	/**
	 * 列表
	 */
	List<MpGoodsCategoryEntity> queryList(Map<String, Object> map); 
	int queryTotal(Map<String, Object> map);
	/**
	 * 增加
	 */
	void save(MpGoodsCategoryEntity entity);
	/**
	 * 修改
	 */
	void update(MpGoodsCategoryEntity entity);
	/**
	 * 查看
	 */
	MpGoodsCategoryEntity queryObject(long id);
	/**
	 * 删除
	 * @param menuIds
	 */
	void deleteBatch(long[] menuIds);
	
	/**
	 * 根据商品分类名称查找
	 * @param categoryName
	 * @return
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
