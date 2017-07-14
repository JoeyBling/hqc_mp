package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpProjectCategoryEntity;

/**
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
public interface ProjectCategoryService {

	/**
	 * 列表
	 * @param map
	 * @return
	 */
	List<MpProjectCategoryEntity> queryList(Map<String, Object> map);

	/**
	 * 总数
	 * @param map
	 * @return
	 */
	int queryToall(Map<String, Object> map);

	/**
	 * 删除
	 * @param id
	 */
	int deletePath(long[] id);

	/**
	 * 根据id查询修改
	 * @param id
	 * @return
	 */
	List<MpProjectCategoryEntity> queryByIds(long[] id);

	/**
	 * 保存
	 * @param category
	 */
	void save(MpProjectCategoryEntity category);

	/**
	 * 根据id查询信息
	 * @param id
	 * @return
	 */
	public MpProjectCategoryEntity queryObject(long id);

	/**
	 * 修改
	 * @param scenicEntity
	 */
	void update(MpProjectCategoryEntity category);

	/**
	 * 查询父类项目
	 * @return
	 */
	List<MpProjectCategoryEntity> queryListproCategory();

	/**
	 * 验证项目类型是否已存在
	 * @param scenicName
	 * @return
	 */
	Integer queryByName(String categoryName);
	
}
