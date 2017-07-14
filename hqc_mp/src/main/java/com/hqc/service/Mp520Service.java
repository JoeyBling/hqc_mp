package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.Mp520Entity;

/**
 * 东部华侨城520活动记录服务接口
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface Mp520Service {

	/**
	 * 保存一个520活动记录
	 * 
	 * @param mp520
	 *            东部华侨城520活动记录表
	 * @return 影响行数
	 */
	int save(Mp520Entity mp520);

	/**
	 * 查询列表
	 * 
	 * @param map
	 * @return 东部华侨城520活动记录列表
	 */
	List<Mp520Entity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询东部华侨城520活动记录
	 * 
	 * @param id
	 *            东部华侨城520活动记录ID
	 * @return 东部华侨城520活动记录表
	 */
	Mp520Entity queryObject(Long id);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 查询匹配总数
	 * 
	 * @param match
	 *            (0查询未匹配成功的总数,1查询待匹配男的随机数总数，2查询待匹配女的随机数总数)
	 * @return 匹配总数
	 */
	int queryMatchTotal(Integer match);

	/**
	 * 根据生成的唯一数字标识查询
	 * 
	 * @param num
	 *            生成的唯一数字标识
	 * @return Mp520Entity
	 */
	Mp520Entity getByNum(Integer num);

	/**
	 * 根据男OpenID查询520活动记录表
	 * 
	 * @param openId
	 *            公众号标识男openid
	 * @return Mp520Entity
	 */
	Mp520Entity manByOpenId(String openId);

	/**
	 * 根据女OpenID查询520活动记录表
	 * 
	 * @param openId
	 *            公众号标识女openid
	 * @return Mp520Entity
	 */
	Mp520Entity womanByOpenId(String openId);

	/**
	 * 根据OpenID查询520活动记录表
	 * 
	 * @param openId
	 *            公众号标识openid
	 * @return Mp520Entity
	 */
	Mp520Entity queryByOpenIdAny(String openId);

	/**
	 * 获得一个随机数(随机的活动记录)
	 * 
	 * @param status
	 *            (0查询未匹配成功,1查询待匹配男的，2查询待匹配女的)
	 * @return Mp520Entity
	 */
	Mp520Entity getRandomByOne(Integer status);

	/**
	 * 根据ID更新一个活动记录
	 * 
	 * @param mp520
	 *            活动记录
	 * @return 影响行数
	 */
	int updateByPrimaryKey(Mp520Entity mp520);

}
