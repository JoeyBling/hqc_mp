package com.hqc.dao;

import java.util.List;

import com.hqc.entity.MpProjectCategoryEntity;

/**
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
public interface ProjectCategoryDao extends BaseDao<MpProjectCategoryEntity> {

	/**
	 * 根据id查询修改
	 * @param id
	 * @return
	 */
	List<MpProjectCategoryEntity> queryListByIds(long[] id);

	/**
	 * 查询父类项目
	 * @return
	 */
	List<MpProjectCategoryEntity> queryListproCategory();

	/**
	 * 验证项目类型是否已存在
	 * @param categoryName
	 * @return
	 */
	Integer queryByName(String categoryName);

}
