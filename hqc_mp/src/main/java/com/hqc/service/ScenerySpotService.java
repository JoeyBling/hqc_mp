package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.entity.MpScenicSpotEntity;

/**
 * 景点后台业务接口
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月15日
 */
public interface ScenerySpotService {

	/**
	 * 查询所有景点
	 * @param map
	 * @return
	 */
	public List<MpScenerySpotEntity> queryList(Map<String,Object> map);
	/**
	 * 查询总数
	 * @param map
	 * @return
	 */
	public int querytoAll(Map<String,Object> map);
	/**
	 * 删除多个景点
	 * @param id
	 */
	public void deletePath(long[] id);		
	/**
	 * 查询所属景区
	 * @return
	 */
	public List<MpScenicSpotEntity> queryListscenic();
	/**
	 * 保存
	 * @param sceneryEntity
	 */
	public void save(MpScenerySpotEntity sceneryEntity);
	/**
	 * 修改
	 * @param sceneryEntity
	 */
	public void update(MpScenerySpotEntity sceneryEntity);
	/**
	 * 查看修改信息
	 * @param id
	 * @return
	 */
	public MpScenerySpotEntity queryObject(long id);
	/**
	 * 根据景区ID查询
	 * @param scenicId
	 * @return
	 */
	public MpScenicSpotEntity queryscenicById(Long scenicId);
	
	/**
	 * 验证景景点名称是否存在
	 * @param scenicName
	 * @return
	 */
	public Integer queryByName(String scenicName);
	/**
	 * 根据景区Id查景点
	 * @param id
	 */
	public List<MpScenerySpotEntity> queryObjectList(long[] id);
	/**
	 * 根据景点Id判断是否存在子项景点项目
	 * @param ids
	 * @return
	 */
	public int queryObjectAll(long[] ids);
	
}
