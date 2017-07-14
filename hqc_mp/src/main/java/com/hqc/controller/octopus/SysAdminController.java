package com.hqc.controller.octopus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.SysAdminEntity;
import com.hqc.service.SysAdminRoleService;
import com.hqc.service.SysAdminService;
import com.hqc.util.Constant;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.ShiroUtils;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/octopus/sys/user")
public class SysAdminController extends AbstractController {

	@Resource
	private SysAdminService sysAdminService;
	@Resource
	private SysAdminRoleService sysAdminRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(Integer page, Integer limit, Integer checkpage,
			String username, HttpServletRequest request) {
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "`create_time` desc"); // 排序
		map.put("username", username == null ? null : username.trim());
		// 查询列表数据
		List<SysAdminEntity> list = sysAdminService.queryList(map);
		int total = sysAdminService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info() {
		return R.ok().put("user", getAdmin());
	}

	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/password")
	@RequiresPermissions("sys:user:update")
	public R password(String password, String newPassword) {
		if (StringUtils.isBlank(newPassword)) {
			return R.error("新密码不为能空");
		}

		// sha256加密
		password = new Sha256Hash(password).toHex();
		// sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();

		// 更新密码
		int count = sysAdminService.updatePassword(getAdminId(), password,
				newPassword);
		if (count == 0) {
			return R.error("原密码不正确");
		}
		// 退出
		ShiroUtils.logout();
		return R.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId) {
		SysAdminEntity user = sysAdminService.queryObject(userId);

		// 获取用户所属的角色列表
		List<Long> roleIdList = sysAdminRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);

		return R.ok().put("user", user);
	}

	/**
	 * 保存用户
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(SysAdminEntity user, String role) throws Exception {
		if (StringUtils.isBlank(role)) {
			return R.error("请为用户赋予至少一个权限");
		}
		String[] roleList = role.split(",");
		List<Long> roleIdList = new ArrayList<Long>();
		for (String string : roleList) {
			roleIdList.add(new Long(string));
		}
		user.setRoleIdList(roleIdList);
		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return R.error("密码不能为空");
		}
		sysAdminService.save(user);
		return R.ok();
	}

	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(SysAdminEntity user, String role) {
		if (StringUtils.isBlank(role)) {
			return R.error("请为用户赋予至少一个权限");
		}
		String[] roleList = role.split(",");
		List<Long> roleIdList = new ArrayList<Long>();
		for (String string : roleList) {
			roleIdList.add(new Long(string));
		}
		user.setRoleIdList(roleIdList);

		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}

		if (ShiroUtils.getUserId().equals(user.getUserId())) {
			if (user.getStatus().equals(0)) {
				ShiroUtils.logout(); // 退出
			}
		}
		sysAdminService.update(user);
		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(String userId) {
		if (StringUtils.isBlank(userId)) {
			return R.error("删除的用户为空");
		}
		long[] userIds = new long[userId.split(",").length];
		for (int i = 0; i < userId.split(",").length; i++) {
			userIds[i] = Long.valueOf(userId.split(",")[i]);
		}
		if (ArrayUtils.contains(userIds, Constant.getAdminId())) {
			return R.error("系统管理员不能删除");
		}
		if (ArrayUtils.contains(userIds, getAdminId())) {
			return R.error("当前用户不能删除");
		}

		sysAdminService.deleteBatch(userIds);

		return R.ok();
	}

	/**
	 * 自己的个人信息
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public R view() {
		// 从session中获取Id；
		Long userId = getAdminId();
		SysAdminEntity user = this.sysAdminService.queryObject(userId);
		List<String> roleNames = null;
		if (getAdminId() != 1) {
			roleNames = this.sysAdminRoleService.queryRoleNames(userId);
		} else {
			roleNames = new ArrayList<>();
			roleNames.add("系统管理员");
		}

		R r = R.ok();
		r.put("user", user);
		r.put("roleNames", roleNames);
		return r;
	}

	/**
	 * 
	 */
	@RequestMapping("/updateView")
	public R updateView(SysAdminEntity user) {
		if (StringUtils.isBlank(user.getEmail())) {
			return R.error("请输入邮箱");
		}
		if (StringUtils.isBlank(user.getMobile())) {
			return R.error("请输入手机号");
		}

		user.setUserId(getAdminId());
		int count = this.sysAdminService.updateSelf(user);
		if (count != 1) {
			return R.error("修改失败！");
		}
		return R.ok();
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @param secPassword
	 * @return
	 */
	@RequestMapping("/updateSelfPassword")
	public R updateSelfPassword(String oldPassword, String newPassword,
			String secPassword) {
		if (StringUtils.isBlank(oldPassword)) {
			return R.error("请输入原密码！");
		}
		if (StringUtils.isBlank(newPassword)) {
			return R.error("请输入新密码！");
		}
		if (StringUtils.isBlank(secPassword)) {
			return R.error("请确认新密码！");
		}
		if (!newPassword.equals(secPassword)) {
			return R.error("两次密码输入不同！");
		}
		oldPassword = new Sha256Hash(oldPassword).toHex();
		newPassword = new Sha256Hash(newPassword).toHex();
		int count = this.sysAdminService.updatePassword(getAdminId(),
				oldPassword, newPassword);
		if (count != 1) {
			return R.error("原密码输入错误！");
		}
		return R.ok();
	}
}
