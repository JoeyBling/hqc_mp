package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpScenicSpotEntity;

/**
 * 景区后台业务接口
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月16日
 */
public interface ScenicSpotService {

	/**
	 * 列表
	 * @param map
	 * @return
	 */
	List<MpScenicSpotEntity> queryList(Map<String, Object> map);

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
	public void deletePath(long[] id);
	/**
	 * 保存
	 * @param scenic
	 */
	public void save(MpScenicSpotEntity scenic);
	/**
	 * 修改
	 * @param scenic
	 */
	public void update(MpScenicSpotEntity scenic);

	/**
	 * 根据id修改查询得到
	 * @param id
	 * @return
	 */
	public  MpScenicSpotEntity queryObject(long id);

	/**
	 * 判断存在与否
	 * @param scenicName
	 * @return
	 */
	boolean queryScenicName(String scenicName);

	/**
	 * 验证景区名称是否存在
	 * @param scenicName
	 * @return
	 */
	Integer queryByName(String scenicName);
	

}
