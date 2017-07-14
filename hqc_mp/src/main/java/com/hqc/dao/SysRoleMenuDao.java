package com.hqc.dao;

import java.util.List;

import com.hqc.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:46
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

	/**
	 * 根据角色ID，获取菜单ID列表
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
