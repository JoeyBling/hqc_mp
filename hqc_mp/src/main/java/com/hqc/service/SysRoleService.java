package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.SysRoleEntity;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {

	/**
	 * 根据角色ID查询角色
	 * 
	 * @param roleId
	 *            角色ID
	 * @return SysRoleEntity
	 */
	SysRoleEntity queryObject(Long roleId);

	/**
	 * 查询角色列表
	 * 
	 * @param map
	 *            Map
	 * @return List<SysRoleEntity>
	 */
	List<SysRoleEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存角色
	 * 
	 * @param role
	 *            SysRoleEntity
	 */
	void save(SysRoleEntity role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            SysRoleEntity
	 */
	void update(SysRoleEntity role);

	/**
	 * 根据角色ID删除角色
	 * 
	 * @param roleIds
	 *            角色ID
	 */
	void deleteBatch(long[] roleIds);
}
