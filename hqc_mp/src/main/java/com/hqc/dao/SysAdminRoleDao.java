package com.hqc.dao;

import java.util.List;

import com.hqc.entity.SysAdminRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:46
 */
public interface SysAdminRoleDao extends BaseDao<SysAdminRoleEntity> {

	/**
	 * 根据用户ID，获取角色ID列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
	
	/**
	 * 根据用户ID，获取角色名字列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色名字列表
	 */
	List<String> queryRoleNames(Long userId);
}
