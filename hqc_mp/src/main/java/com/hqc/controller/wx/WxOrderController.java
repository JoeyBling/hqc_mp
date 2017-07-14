package com.hqc.controller.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpTicketOrderEntity;
import com.hqc.service.MpTicketOrderService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 微信订单
 * 
 * @author cxw
 * @date 2017年5月26日
 */
@Controller
@RequestMapping("/wx/order")
public class WxOrderController extends WxAuthController {

	@Resource
	private MpTicketOrderService mpTicketOrderService;

	/**
	 * 我的订单
	 * 
	 * @param type
	 *            1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            Model
	 * @return FTL
	 */
	@RequestMapping("/order")
	public String order(Integer type, HttpServletRequest request, Model model) {
		if (!isLogin(request)) {
			return "/wx/404.ftl";
		}
		int limit = 5; // 每页显示5条
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", limit);
		map.put("type", type);
		// 显示未删除的
		map.put("del", 0);
		// 时间降序
		map.put("orderby", "o.`status` = 3 DESC,o.`create_time` DESC");
		map.put("memberId", getMember(request).getId());
		// 可能直接在小程序下单的也要显示
		map.put("unionid", getMember(request).getUnionid());
		List<MpTicketOrderEntity> list = mpTicketOrderService.queryList(map);
		model.addAttribute("list", list);
		model.addAttribute("type", type);
		return "/wx/user/order.ftl";
	}

	/**
	 * 订单记录
	 * 
	 * @param page
	 *            当前页码
	 * @param type
	 *            1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	@RequestMapping("/record")
	@ResponseBody
	public R record(Integer page, Integer type, HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		int limit = 3; // 每页显示5条
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("type", type);
		// 显示未删除的
		map.put("del", 0);
		// 时间降序
		map.put("order", "o.`create_time` desc");
		map.put("memberId", getMember(request).getId());
		// 可能直接在小程序下单的也要显示
		map.put("unionid", getMember(request).getUnionid());
		List<MpTicketOrderEntity> list = mpTicketOrderService.queryList(map);
		int total = mpTicketOrderService.queryTotal(map);
		PageUtils pageutil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageutil);
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 *            订单ID
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	@RequestMapping("/delOrder")
	@ResponseBody
	public R deleteOrder(Long id, HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpTicketOrderEntity queryObject = mpTicketOrderService.queryObject(id);
		MpMemberEntity member = getMember(request);
		// 有可能小程序直接下的单
		if (null == queryObject
				|| (!queryObject.getUnionid().equals(member.getUnionid()) || !queryObject
						.getMemberId().equals(member.getId()))) {
		} else {
			if (mpTicketOrderService.delByWhere(id, member.getId(), 1) > 0)
				return R.ok("删除订单成功!");
		}
		return R.error("删除订单失败!");
	}

	/**
	 * 取消订单
	 * 
	 * @param id
	 *            订单ID
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	@RequestMapping("/canOrder")
	@ResponseBody
	public R canOrder(Long id, HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpTicketOrderEntity queryObject = mpTicketOrderService.queryObject(id);
		MpMemberEntity member = getMember(request);
		// 有可能小程序直接下的单
		if (null == queryObject
				|| (!queryObject.getUnionid().equals(member.getUnionid()) || !queryObject
						.getMemberId().equals(member.getId()))
				|| queryObject.getStatus() == 1) {

		} else {
			if (mpTicketOrderService.updateStatusByWhere(id, member.getId(), 4) > 0)
				return R.ok("取消订单成功!");
		}
		return R.error("取消订单失败!");
	}

	/**
	 * 订单详细
	 * 
	 * @param id
	 *            购票订单记录表ID
	 * @param model
	 *            Model
	 * @param request
	 *            HttpServletRequest
	 * @return FTL
	 */
	@RequestMapping("/detail")
	public String detail(Long id, Model model, HttpServletRequest request) {
		if (!isLogin(request)) {
			return "/wx/user/login.ftl";
		}
		MpTicketOrderEntity queryObject = mpTicketOrderService.queryObject(id);
		MpMemberEntity member = getMember(request);
		// 有可能小程序直接下的单
		if (null == queryObject
				|| (!queryObject.getUnionid().equals(member.getUnionid()) || !queryObject
						.getMemberId().equals(member.getId()))) {
			return "/wx/404.ftl";
		} else {
			model.addAttribute("entity", queryObject);
		}
		return "/wx/user/orderDetail.ftl";
	}
}
