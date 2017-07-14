package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpGoodsCategoryEntity;
import com.hqc.entity.MpGoodsEntity;


public interface GoodsDao extends BaseDao<MpGoodsEntity> {
	/**
	 * 获取商品类别
	 */
	public List<MpGoodsCategoryEntity> queryAllGategory();
	/**
	 * 根据id获取商品类别
	 */
	public MpGoodsCategoryEntity queryCategoryById(Long id);
	/**
	 * 上架 下架
	 */
	public void auitStatus(MpGoodsEntity entity);
	/**
	 * 验证商品名称是否已存在
	 * @param map
	 * @return
	 */
	int queryGoodsByName(Map<String, Object> map);
	/**
	 * 验证改商品是否被使用
	 */
	int queryUsingGoods(Long id);
	

}
