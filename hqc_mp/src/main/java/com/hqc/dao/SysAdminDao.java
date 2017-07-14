package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.SysAdminEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysAdminDao extends BaseDao<SysAdminEntity> {

	/**
	 * 查询用户的所有权限
	 * 
	 * @param userId
	 *            用户ID
	 * @return List<String>
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 * 
	 * @param userId
	 *            用户ID
	 * @return List<Long>
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 * 
	 * @param username
	 *            用户名
	 * @return 用户名
	 */
	SysAdminEntity queryByUserName(String username);

	/**
	 * 修改密码
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int updatePassword(Map<String, Object> map);
}
