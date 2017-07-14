package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.GoodsCategoryDao;
import com.hqc.entity.MpGoodsCategoryEntity;


/**
 * 商品分类
 * @author Administrator
 *
 */
@Service("goodsCategoryService")
public class GoodsCategoryService implements com.hqc.service.GoodsCategoryService {
	@Resource
	private GoodsCategoryDao goodsCategoryDao;
	
	/**
	 * 列表
	 */
	public List<MpGoodsCategoryEntity> queryList(Map<String, Object> map){
		return goodsCategoryDao.queryList(map);
	}
	public int queryTotal(Map<String, Object> map){
		return goodsCategoryDao.queryTotal(map);
	}
	/**
	 * 增加
	 */
	public void save(MpGoodsCategoryEntity entity){
		goodsCategoryDao.save(entity);
	}
	/**
	 * 修改
	 */
	public void update(MpGoodsCategoryEntity entity){
		goodsCategoryDao.update(entity);
	}
	/**
	 * 查看
	 */
	public MpGoodsCategoryEntity queryObject(long id){
		return goodsCategoryDao.queryObject(id);
	}
	/**
	 * 删除
	 * @param menuIds
	 */
	public void deleteBatch(long[] ids){
		goodsCategoryDao.deleteBatch(ids);
	};
	/**
	 * 根据商品类型名称查找该类型是否存在
	 */
	public int queryTotalByName(MpGoodsCategoryEntity data){
		return goodsCategoryDao.queryTotalByName(data);
	}
	/**
	 * 查询该类商品是否被使用
	 */
	public int queryUsingCategory(Long id){
		return goodsCategoryDao.queryUsingCategory(id);
	}
	/**
	 * 删除该类下所有商品
	 */
	public void deleteGoodsByCategory(Long categoryId){
		goodsCategoryDao.deleteGoodsByCategory(categoryId);
	}

}
