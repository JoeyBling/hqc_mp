package com.hqc.dao;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.Mp520Entity;

/**
 * 东部华侨城520活动记录
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface Mp520Dao extends BaseDao<Mp520Entity> {

	/**
	 * 根据生成的唯一数字标识查询
	 * 
	 * @param randomNumber
	 *            生成的唯一数字标识
	 * @return Mp520Entity
	 */
	Mp520Entity getByNum(Integer randomNumber);

	/**
	 * 根据OpenID查询520活动记录表
	 * 
	 * @param openId
	 *            公众号标识openid
	 * @param sex
	 *            男1女2
	 * @return Mp520Entity
	 */
	Mp520Entity queryByOpenId(@Param("openId") String openId,
			@Param("sex") int sex);

	/**
	 * 根据OpenID查询520活动记录表(2个都进行查询)
	 * 
	 * @param openId
	 *            公众号标识openid
	 * @return Mp520Entity
	 */
	Mp520Entity queryByOpenIdAny(String openId);

	/**
	 * 查询匹配总数
	 * 
	 * @param match
	 *            (0查询未匹配成功的总数,1查询待匹配男的随机数总数，2查询待匹配女的随机数总数)
	 * @return 匹配总数
	 */
	int queryMatchTotal(Integer match);

	/**
	 * 获得一个随机数(随机的活动记录)
	 * 
	 * @param status
	 *            (0查询未匹配成功,1查询待匹配男的，2查询待匹配女的)
	 * @return Mp520Entity
	 */
	Mp520Entity getRandomByOne(Integer status);
}
