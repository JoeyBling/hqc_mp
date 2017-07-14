package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.service.GoodsExchangeService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 商品兑换
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/octopus/goods/exchange")
public class GoodsExchangeController extends AbstractController {
	@Resource
	private GoodsExchangeService goodsExchangeService;

	/**
	 * 积分商品兑换记录列表
	 * 
	 * @param page
	 *            当前页码
	 * @param checkpage
	 *            下一页或者上一页
	 * @param limit
	 *            每页显示的条数
	 * @param trueName
	 *            会员名称
	 * @param goodsName
	 *            兑换商品名称
	 * @param useStatus
	 *            使用状态
	 * @return Map
	 */
	@RequestMapping("/list")
	@RequiresPermissions("goods:exchange:list")
	@ResponseBody
	public R list(Integer page, Integer checkpage, Integer limit,
			String trueName, String goodsName, String useStatus,String personPhone ) {
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
		map.put("orderby", "id desc"); // 排序
		map.put("trueName", trueName==null?null:trueName.trim());
		map.put("goodsName", goodsName==null?null:goodsName.trim());
		map.put("personPhone", personPhone==null?null:personPhone.trim());
		map.put("useStatus", useStatus);
		List<Map<String, Object>> list = goodsExchangeService
				.queryList(map);
		int total = goodsExchangeService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 删除积分商品兑换记录
	 * 
	 * @param ids
	 *            积分商品兑换记录ID
	 * @return Map
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("goods:exchange:delete")
	@ResponseBody
	public R delete(long[] ids) {
		goodsExchangeService.deleteBatch(ids);
		return R.ok();
	}

}
