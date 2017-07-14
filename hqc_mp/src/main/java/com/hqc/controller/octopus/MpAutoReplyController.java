package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpAutoReplyEntity;
import com.hqc.service.MpAutoReplyService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 微信公众号自动回复控制器
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月18日
 * 
 */
@RestController
@RequestMapping("/octopus/mp/autoreply")
public class MpAutoReplyController {

	@Resource
	private MpAutoReplyService mpAutoReplyService;

	/**
	 * 微信公众号自动回复列表信息
	 * 
	 * @param page
	 *            当前页码
	 * @param checkpage
	 *            上一页
	 * @param limit
	 *            每页显示的数量
	 * @return Map
	 */
	@RequestMapping("/list")
	@RequiresPermissions("mp:autoreply:list")
	public R querylist(Integer page, Integer checkpage, Integer limit) {
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();

		map.put("orderby", "response_type");
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		List<MpAutoReplyEntity> list = mpAutoReplyService.queryList(map);
		int total = mpAutoReplyService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 保存一个自动回复
	 * 
	 * @param entity
	 *            公众号自动回复表
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("mp:autoreply:save")
	public R save(MpAutoReplyEntity entity) throws Exception {
		int save = mpAutoReplyService.save(entity);
		if (save > 0) {
			return R.ok();
		} else {
			return R.error("添加失败，请重试!");
		}
	}

	/**
	 * 自动回复信息
	 * 
	 * @param id
	 *            自动回复ID
	 * @return Map
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("mp:autoreply:info")
	public R info(@PathVariable("id") Long id) {
		MpAutoReplyEntity entity = mpAutoReplyService.queryObject(id);
		return R.ok().put("entity", entity);
	}

	/**
	 * 修改自动回复
	 */
	@RequestMapping("/update")
	@RequiresPermissions("mp:autoreply:update")
	public R update(MpAutoReplyEntity entity) {
		mpAutoReplyService.updateByPrimaryKey(entity);
		return R.ok();
	}

	/**
	 * 删除自动回复
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("mp:autoreply:delete")
	public R delete(String replyId) {
		if (StringUtils.isBlank(replyId)) {
			return R.error("删除的自动回复为空");
		}
		long[] replyIds = new long[replyId.split(",").length];
		for (int i = 0; i < replyId.split(",").length; i++) {
			replyIds[i] = Long.valueOf(replyId.split(",")[i]);
		}
		mpAutoReplyService.deleteBatch(replyIds);

		return R.ok();
	}

}
