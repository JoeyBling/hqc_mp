package com.hqc.dao;

import java.util.List;

import com.hqc.entity.SysMenuEntity;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param parentId
	 *            父菜单ID
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 * 
	 * @return List<SysMenuEntity>
	 */
	List<SysMenuEntity> queryNotButtonList();
}
