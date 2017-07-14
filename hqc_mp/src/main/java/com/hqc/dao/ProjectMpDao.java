package com.hqc.dao;

import java.util.List;

import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.entity.MpProjectEntity;
import com.hqc.entity.MpScenerySpotEntity;

/**
 * 项目DAO
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月18日
 */
public interface ProjectMpDao extends BaseDao<MpProjectEntity>{

	/**
	 * 修改得到项目类型
	 * @return
	 */
	List<MpProjectCategoryEntity> queryListCategory();

	/**
	 * 修改得到景点名称
	 * @return
	 */
	List<MpScenerySpotEntity> queryListScenery();

	/**
	 * 修改时根据景点id查询景点
	 * @param sceneryId
	 * @return
	 */
	MpScenerySpotEntity querysceneryById(Long sceneryId);

	/**
	 * 修改时根据项目类型id查询项目类型
	 * @param categoryId
	 * @return
	 */
	MpProjectCategoryEntity querycategoryById(Long categoryId);

	/**
	 * 根据id数组查询项目类型
	 * @param id
	 * @return
	 */
	List<MpProjectEntity> queryLists(long[] id);

	/**
	 * 删除
	 * @param id
	 */
	void deleteCascade(long[] ids);

	/**
	 * 查询项目是否存在
	 * @param projectName
	 * @return
	 */
	Integer queryByName(String projectName);

	/**
	 * 根据景点Id判断是否存在子项景点项目
	 * @param ids
	 * @return
	 */
	int queryObjectAll(long[] ids);

	/**
	 * 根据项目类型Id判断是否存在子项景点
	 * @param categoryIds
	 * @return
	 */
	int queryCategorytAll(long[] categoryIds);

	/**
	 * 根据项目类型名称查询属于该项目类型的景点
	 * @param id
	 * @return
	 */
	List<MpProjectEntity> queryListBywhere(Long  id);
}
