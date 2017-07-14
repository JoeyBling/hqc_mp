package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpSmsCodeEntity;

/**
 * 手机短信验证码服务接口
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
public interface MpSmsCodeService {

	/**
	 * 保存一个手机短信验证码
	 * 
	 * @param entity
	 *            手机短信验证码
	 * @return 影响行数
	 */
	int save(MpSmsCodeEntity entity);

	/**
	 * 查询手机短信验证码列表
	 * 
	 * @param map
	 * @return 手机短信验证码列表
	 */
	List<MpSmsCodeEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询手机短信验证码
	 * 
	 * @param id
	 *            ID
	 * @return 手机短信验证码
	 */
	MpSmsCodeEntity queryObject(Long id);

	/**
	 * 查询手机短信验证码总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 手机短信验证码总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个手机短信验证码
	 * 
	 * @param entity
	 *            手机短信验证码
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpSmsCodeEntity entity);

	/**
	 * 根据ID删除手机短信验证码
	 * 
	 * @param id
	 *            ID
	 * @return 影响行数
	 */
	int delete(Long id);
}
