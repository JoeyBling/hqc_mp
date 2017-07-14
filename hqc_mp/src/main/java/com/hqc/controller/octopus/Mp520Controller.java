package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.Mp520Entity;
import com.hqc.service.Mp520Service;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 520活动控制器
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月18日
 * 
 */
@Controller
@RequestMapping("/octopus/activity/520")
public class Mp520Controller {

	@Resource
	private Mp520Service mp520Service;

	/**
	 * 获取所有配对的列表信息
	 * 
	 * @param randomNumber
	 *            活动生成号码
	 * @param manNickName
	 *            男方微信昵称
	 * @param womanNickName
	 *            女方微信昵称
	 * @param page
	 *            当前页码
	 * @param checkpage
	 *            上一页
	 * @param limit
	 *            每页显示的数量
	 * @return Map
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions("active:520:list")
	public R querylist(Integer randomNumber, String manNickName,
			String womanNickName, Integer page, Integer checkpage, Integer limit) {
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		if (manNickName.trim() != "") { // 男方微信昵称
			map.put("manNickName", manNickName);
		}
		if (womanNickName.trim() != "") { // 女方微信昵称
			map.put("womanNickName", womanNickName);
		}
		// 活动生成号码
		map.put("randomNumber", randomNumber);
		// 显示已配对的用户
		map.put("notNull", true);

		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		List<Mp520Entity> list = mp520Service.queryList(map);
		int total = mp520Service.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

}
