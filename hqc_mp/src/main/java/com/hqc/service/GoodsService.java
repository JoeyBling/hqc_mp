package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpGoodsCategoryEntity;
import com.hqc.entity.MpGoodsEntity;

/**
 * 商品
 * @author Administrator
 *
 */
public interface GoodsService {
	/**
	 * 列表
	 */
	List<MpGoodsEntity> queryList(Map<String, Object> map); 
	int queryTotal(Map<String, Object> map);
	/**
	 * 获取商品类别
	 */
	 List<MpGoodsCategoryEntity> queryAllGategory();
	 /**
	  * 增加
	  */
	 void save(MpGoodsEntity entity);
	 /**
	  * 查找
	  * @param id
	  * @return
	  */
	 MpGoodsEntity  queryObject(Long id);
		/**
		 * 根据id获取商品类别
		 */
     MpGoodsCategoryEntity queryCategoryById(Long id);
     /**
      * 修改
      */
     void update(MpGoodsEntity entity);
     /**
      * 删除
      */
     void deleteBatch(long[] ids);
 	/**
 	 * 上架 下架
 	 */
    void auitStatus(MpGoodsEntity entity);
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
