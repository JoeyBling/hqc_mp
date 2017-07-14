package com.hqc.service;

import java.util.List;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:24
 */
public interface SysAdminRoleService {

	/**
	 * 保存用户与角色对应关系
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleIdList
	 *            角色ID列表
	 */
	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户ID，获取角色ID列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return 角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据用户ID，删除角色ID
	 * 
	 * @param userId
	 *            用户ID
	 */
	void delete(Long userId);
	
	List<String> queryRoleNames(Long userId);
}
