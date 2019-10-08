package com.hqc.service.impl;

import com.hqc.dao.SysAdminRoleDao;
import com.hqc.service.SysAdminRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户与角色对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysAdminRoleServiceImpl implements SysAdminRoleService {

    @Resource
    private SysAdminRoleDao sysUserRoleDao;

    @Override
    @Transactional
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        // 先删除用户与角色关系
        sysUserRoleDao.deleteById(userId);

        // 保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        sysUserRoleDao.save(map);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }

    @Override
    public void delete(Long userId) {
        sysUserRoleDao.deleteById(userId);
    }

    @Override
    public List<String> queryRoleNames(Long userId) {

        return sysUserRoleDao.queryRoleNames(userId);
    }
}
