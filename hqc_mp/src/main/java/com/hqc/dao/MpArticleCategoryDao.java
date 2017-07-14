package com.hqc.dao;

import java.util.List;

import com.hqc.entity.MpArticleCategoryEntity;

public interface MpArticleCategoryDao extends BaseDao<MpArticleCategoryEntity> {

	List<MpArticleCategoryEntity> querybyparent();

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
	 * 查询标题是否存在
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
