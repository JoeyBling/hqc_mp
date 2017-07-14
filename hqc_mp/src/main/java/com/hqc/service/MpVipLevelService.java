package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpVipLevelEntity;

public interface MpVipLevelService {
	/**
	 * 根据id做查询
	 * 
	 * @param id
	 * @return
	 */
	MpVipLevelEntity queryMpVipLevelInfoId(long id);

	/**
	 * 修改
	 * 
	 * @param vip_name
	 * @return
	 */
	void update(MpVipLevelEntity mpVipLevelEntity);

	/**
	 * 添加
	 * 
	 * @param mpVipLevelEntity
	 */
	void save(MpVipLevelEntity mpVipLevelEntity);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void deleteBatch(long[] id);

	/**
	 * 根据会员级别做查询
	 * 
	 * @param vip_name
	 * @return
	 */
	List<MpVipLevelEntity> queryMpVipLevelName(String vipName);

	/**
	 * 查询条数
	 * 
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 查询所有
	 * 
	 * @param map
	 * @return
	 */
	List<MpVipLevelEntity> queryAllList(Map<String, Object> map);
	/**
	 * 查询最大积分
	 * @param vipName
	 * @return
	 */
	long queryMaxIntegral();

	/**
	 * 获取最小等级的
	 * 
	 * @return MpVipLevelEntity
	 */
	MpVipLevelEntity getMin();

	/**
	 * 获取最大等级的
	 * 
	 * @return MpVipLevelEntity
	 */
	MpVipLevelEntity getMax();

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
