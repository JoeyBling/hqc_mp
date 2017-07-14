package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpBannerEntity;

/**
 * 横幅图片服务接口
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpBannerService {

	/**
	 * 保存一个横幅图片
	 * 
	 * @param entity
	 *            横幅图片
	 * @return 影响行数
	 */
	int save(MpBannerEntity entity);

	/**
	 * 查询列表
	 * 
	 * @param map
	 * @return 横幅图片列表
	 */
	List<MpBannerEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询横幅图片
	 * 
	 * @param id
	 *            横幅图片ID
	 * @return 横幅图片表
	 */
	MpBannerEntity queryObject(Long id);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个横幅图片
	 * 
	 * @param entity
	 *            横幅图片
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpBannerEntity entity);

	/**
	 * 根据ID删除横幅图片记录
	 * 
	 * @param id
	 *            ID
	 * @return 影响行数
	 */
	int delete(Long id);
	
	/**
	 * 多行删除
	 * @param ids
	 */
	void deleteBatch(long[] ids); 

}
