package com.hqc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.MpVipLevelEntity;

public interface MpVipLevelDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	MpVipLevelEntity queryMpVipLevelInfoId(long id);

	/**
	 * 
	 * @param vip_name
	 * @return
	 */
	void update(MpVipLevelEntity mpVipLevelEntity);

	/**
	 * 
	 * @param mpVipLevelEntity
	 */
	void save(MpVipLevelEntity mpVipLevelEntity);

	/**
	 * 
	 * @param id
	 */
	void deleteBatch(long[] id);

	/**
	 * 
	 * @param vip_name
	 * @return
	 */
	List<MpVipLevelEntity> queryMpVipLevelName(String vipName);

	/**
	 * 
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	List<MpVipLevelEntity> queryAllList(Map<String, Object> map);
     /**
      * 获取最大积分的最大值
      * @return
      */
	long queryMaxIntegral();

	/**
	 * 获取最大的或者最小的
	 * 
	 * @param integralType
	 *            min_integral最小的 max_integral最大的
	 * @return MpVipLevelEntity
	 */
	MpVipLevelEntity getMinOrMax(@Param("type") String integralType);
	/**
	 * 根据当前积分，查询该积分属于哪个积分等级
	 * @param integral
	 * @return
	 */
	MpVipLevelEntity queryLevel(Long integral);
	/**
	 * 根据会员等级做查询
	 * @param vipName
	 * @return
	 */
	MpVipLevelEntity queryVipLevel(String vipName);
	
}
