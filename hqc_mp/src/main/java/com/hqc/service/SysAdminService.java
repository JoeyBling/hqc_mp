package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.SysAdminEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysAdminService {

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
	 * @return 菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 * 
	 * @param username
	 *            用户名
	 * @return SysUserEntity
	 */
	SysAdminEntity queryByUserName(String username);

	/**
	 * 根据用户ID，查询用户
	 * 
	 * @param userId
	 *            用户ID
	 * @return SysUserEntity
	 */
	SysAdminEntity queryObject(Long userId);

	/**
	 * 查询用户列表
	 * 
	 * @param map
	 *            Map
	 * @return List<SysUserEntity>
	 */
	List<SysAdminEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存用户
	 * 
	 * @param user
	 *            SysUserEntity
	 * @throws Exception
	 */
	void save(SysAdminEntity user) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param user
	 *            SysUserEntity
	 */
	void update(SysAdminEntity user);

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 *            long[]
	 */
	void deleteBatch(long[] userIds);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            原密码
	 * @param newPassword
	 *            新密码
	 * @return int
	 */
	int updatePassword(Long userId, String password, String newPassword);
	
	int updateSelf(SysAdminEntity user);
}
