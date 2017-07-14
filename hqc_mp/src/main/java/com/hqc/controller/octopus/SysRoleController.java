package com.hqc.controller.octopus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.SysRoleEntity;
import com.hqc.service.SysRoleMenuService;
import com.hqc.service.SysRoleService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController 
@RequestMapping("/octopus/sys/role")
public class SysRoleController extends AbstractController {
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(Integer page,String roleName,Integer limit, Integer checkpage, HttpServletRequest request) {
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", roleName == null ? null :roleName.trim());
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		// 查询列表数据
		List<SysRoleEntity> list = sysRoleService.queryList(map);

		int total = sysRoleService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select() {
		// 查询列表数据
		List<SysRoleEntity> list = sysRoleService
				.queryList(new HashMap<String, Object>());

		return R.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId) {
		SysRoleEntity role = sysRoleService.queryObject(roleId);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		return R.ok().put("role", role);
	}

	/**
	 * 保存角色
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	@ResponseBody
	public R save(String roleName, String remark, String menuIds)
			throws ParseException {
		if (StringUtils.isBlank(roleName)) {
			return R.error("角色名称不能为空");
		}
		SysRoleEntity roleEntity = new SysRoleEntity();
		roleEntity.setRoleName(roleName);
		roleEntity.setRemark(remark);
		roleEntity.setCreateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		String[] ids = menuIds.split(",");
		List<Long> menuIdList = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			menuIdList.add(Long.valueOf(ids[i]));
		}
		roleEntity.setMenuIdList(menuIdList);
		sysRoleService.save(roleEntity);
		return R.ok();
	}

	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	@ResponseBody
	public R update(Long roleId, String roleName, String remark, String menuIds) {
		if (StringUtils.isBlank(roleName)) {
			return R.error("角色名称不能为空");
		}
		SysRoleEntity roleEntity = new SysRoleEntity();
		roleEntity.setRoleId(roleId);
		roleEntity.setRoleName(roleName);
		roleEntity.setRemark(remark);
		String[] ids = menuIds.split(",");
		if("".equals(ids[0])){
			return R.error("请为角色授权");
		}
		List<Long> menuIdList = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			menuIdList.add(Long.valueOf(ids[i]));
		}
		roleEntity.setMenuIdList(menuIdList);

		sysRoleService.update(roleEntity);

		return R.ok();
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	@ResponseBody
	public R delete(long[] roleIds) {
		sysRoleService.deleteBatch(roleIds);
		return R.ok();
	}
}
