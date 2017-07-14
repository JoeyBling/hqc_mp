package com.hqc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.dao.SysAdminDao;
import com.hqc.entity.SysAdminEntity;
import com.hqc.service.SysAdminRoleService;
import com.hqc.service.SysAdminService;
import com.hqc.util.DateUtils;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Transactional
@Service("sysAdminService")
public class SysAdminServiceImpl implements SysAdminService {

	@Resource
	private SysAdminDao sysAdminDao;

	@Resource
	private SysAdminRoleService sysUserRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysAdminDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysAdminDao.queryAllMenuId(userId);
	}

	@Override
	public SysAdminEntity queryByUserName(String username) {
		return sysAdminDao.queryByUserName(username);
	}

	@Override
	public SysAdminEntity queryObject(Long userId) {
		return sysAdminDao.queryObject(userId);
	}

	@Override
	// @Cacheable(key = "'allAdminList'")
	// Redis缓存添加
	public List<SysAdminEntity> queryList(Map<String, Object> map) {
		return sysAdminDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysAdminDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysAdminEntity user) throws Exception {
		user.setCreateTime(DateUtils.getCurrentUnixTime());
		// sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		sysAdminDao.save(user);
		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysAdminEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {

			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		sysAdminDao.update(user);

		// 保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(long[] userId) {
		sysAdminDao.deleteBatch(userId);
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysAdminDao.updatePassword(map);
	}

	@Override
	public int updateSelf(SysAdminEntity user) {

		return this.sysAdminDao.update(user);
	}
}
