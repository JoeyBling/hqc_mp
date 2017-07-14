package com.hqc.dao;

import com.hqc.entity.MpScenicSpotEntity;

/**
 * 景区管理Dao
 * 
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月16日
 */
public interface ScenicSpotDao extends BaseDao<MpScenicSpotEntity> {

	/**
	 * 为判断重复查询
	 * 
	 * @param scenicName
	 */
	MpScenicSpotEntity queryEntity(String scenicName);

	/**
	 * 验证景区名称是否存在
	 * @param scenicName
	 * @return
	 */
	Integer queryByName(String scenicName);

}
