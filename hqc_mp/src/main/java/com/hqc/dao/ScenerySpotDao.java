package com.hqc.dao;

import java.util.List;

import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.entity.MpScenicSpotEntity;

/**
 * 景点管理Dao
 * 
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月15日
 */
public interface ScenerySpotDao extends BaseDao<MpScenerySpotEntity> {

	/**
	 *  查询所属景区
	 * @return
	 */
	public List<MpScenicSpotEntity> queryListscenic();

	/**
	 * 根据景区ID查询
	 * @param scenicId
	 * @return
	 */
	public MpScenicSpotEntity queryscenicById(Long scenicId);

	/**
	 * 验证景区名称是否存在
	 * @param scenicName
	 * @return
	 */
	public Integer queryByName(String scenicName);

	/**
	 * 根据景区Id查景点
	 * @param id
	 * @return
	 */
	public List<MpScenerySpotEntity> queryObjectList(long[] id);

	/**
	 * 根据景点Id判断是否存在子项景点项目
	 * @param ids
	 * @return
	 */
	public int queryObjectAll(long[] ids);
}
