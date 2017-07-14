package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpArticleCategoryEntity;

public interface MpArticleCategoryService {

	/**
	 * 查询所有信息分类
	 * 
	 * @param userId
	 * @return list<String>
	 */
	List<MpArticleCategoryEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据图文分类ID查询
	 * 
	 * @param articleId
	 *            图文信息ID
	 * @return 图文信息
	 */
	MpArticleCategoryEntity queryObject(Long articleId);

	/**
	 * 保存一个图文分类
	 * 
	 * @param articleEntity
	 *            图文信息表
	 * @return 影响的行数
	 */
	int save(MpArticleCategoryEntity articleCategoryEntity);

	/*
	 * 查询下拉
	 */
	List<MpArticleCategoryEntity> querybyparent();

	/**
	 * 删除分类信息
	 * 
	 * @param infoId
	 * @return
	 */
	int deleteBatch(String infoId);

	/**
	 * 修改选中分类信息
	 * 
	 * @param articleCategoryEntity
	 */
	void update(MpArticleCategoryEntity articleCategoryEntity);

	int querybyland(Long id);

	/**
	 * 父类根据ID查询子类列表中是否存在文章，存在父类不可删除
	 * 
	 * @param id
	 * @return
	 */
	int queryChild(Long id);

	/**
	 * <!-- ID查询PARENTID看是否存在子类菜单 -->
	 * 
	 * @param parentId
	 * @return
	 */
	int queryExist(Long parentId);

	/**
	 * 查询库中是否存在相同类名称
	 * 
	 * @param name
	 * @return
	 */
	int queryByName(String name);

	/**
	 * 根据名称查询图文信息分类
	 * 
	 * @param name
	 *            图文信息分类名称
	 * @return MpArticleCategoryEntity
	 */
	MpArticleCategoryEntity queryByCategoryName(String name);
}
