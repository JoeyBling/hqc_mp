package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.SysMenuEntity;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */
public interface SysMenuService {

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId
	 *            父菜单ID
	 * @param menuIdList
	 *            用户菜单ID
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 获取不包含按钮的菜单列表
	 * 
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 * 
	 * @param userId
	 *            用户ID
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);

	/**
	 * 根据菜单ID查询菜单
	 * 
	 * @param menuId
	 *            菜单ID
	 * @return SysMenuEntity
	 */
	SysMenuEntity queryObject(Long menuId);

	/**
	 * 查询菜单列表
	 * 
	 * @param map
	 *            Map
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存菜单
	 * 
	 * @param menu
	 *            SysMenuEntity
	 */
	void save(SysMenuEntity menu);

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 *            SysMenuEntity
	 */
	void update(SysMenuEntity menu);

	/**
	 * 根据菜单ID删除菜单
	 * 
	 * @param menuIds
	 *            菜单ID
	 */
	void deleteBatch(long[] menuIds);
}
