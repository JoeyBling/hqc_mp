package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.GoodsDao;
import com.hqc.entity.MpGoodsCategoryEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.service.GoodsService;
/**
 * 商品
 * @author Administrator
 *
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private GoodsDao goodsDao;
	
 
    /**
     * 列表
     */
	public List<MpGoodsEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsDao.queryList(map);
	}
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return goodsDao.queryTotal(map);
	}
	/**
	 * 获取商品类别
	 */
	public List<MpGoodsCategoryEntity> queryAllGategory(){
		return goodsDao.queryAllGategory();
	}
	 /**
	  * 增加
	  */
	 public void save(MpGoodsEntity entity){
		 goodsDao.save(entity);
	 }
	 /**
	  * 查找
	  * @param id
	  * @return
	  */
	 public MpGoodsEntity  queryObject(Long id){
		 return goodsDao.queryObject(id);
	 }
	/**
      * 根据id获取商品类别
	  */
    public  MpGoodsCategoryEntity queryCategoryById(Long id){
    	return goodsDao.queryCategoryById(id);
    }
    /**
     * 修改
     */
    public void update(MpGoodsEntity entity){
    	goodsDao.update(entity);
    }
    /**
     * 删除
     */
    public void deleteBatch(long[] ids){
    	goodsDao.deleteBatch(ids);
    }
    /**
 	 * 上架 下架
 	 */
    public void auitStatus(MpGoodsEntity entity){
    	goodsDao.auitStatus(entity);
    }
    /**
	 * 验证商品名称是否已存在
	 * @param map
	 * @return
	 */
	public int queryGoodsByName(Map<String, Object> map){
		return goodsDao.queryGoodsByName(map);
	}
	/**
	 * 验证改商品是否被使用
	 */
	public int queryUsingGoods(Long id){
		return goodsDao.queryUsingGoods(id);
	}
}
