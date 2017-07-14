package com.hqc.controller.wx;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hqc.entity.MpCashCouponHelpEntity;
import com.hqc.entity.MpBannerEntity;
import com.hqc.entity.MpTicketEntity;
import com.hqc.service.MpBannerService;
import com.hqc.service.MpCashCouponHelpService;
import com.hqc.service.MpTicketService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 微信购票
 * 
 * @author cxw
 * @date 2017年5月26日
 */
@Controller
@RequestMapping("/wx/ticket")
public class WxTicketController extends WxAuthController {

	@Resource
	private MpBannerService mpBannerService;
	@Resource
	private MpTicketService ticketService;
	@Resource
	private MpCashCouponHelpService mpCashCouponHelpService;

	@ResponseBody
	@RequestMapping("getTicketList")
	public R getTicketList(Integer page, Integer queryBanner,
			HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Integer limit = 5;
		// 添加当前时间条件，过期的票不再显示
		map.put("nowTime", DateUtils.getCurrentUnixTime());
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		map.put("status", 1);
		List<MpTicketEntity> list = this.ticketService.queryList(map);
		int total = this.ticketService.queryTotal(map);
		Map<String, Object> bannerMap = new HashMap<>();
		// 下拉获取更多门票时，不用再次获取横幅图片
		R r = R.ok();
		if (queryBanner != null && queryBanner == 1) {
			bannerMap.put("limit", 5);
			bannerMap.put("offset", 0);
			bannerMap.put("status", 1);
			List<MpBannerEntity> banneres = this.mpBannerService
					.queryList(bannerMap);
			r.put("banneres", banneres);
		}

		PageUtils pageUtil = new PageUtils(list, total, 5, 0);
		return r.put("page", pageUtil);
	}

	/**
	 * 门票详细信息
	 * 
	 * @param id
	 *            门票ID
	 * @return ModelAndView
	 * @throws ParseException
	 */
	@RequestMapping("ticketInfo")
	public ModelAndView ticketInfo(Long id, HttpServletRequest request)
			throws ParseException {
		ModelAndView view = new ModelAndView();
		if (!isLogin(request)) {
			view.setViewName("/wx/404.ftl");
			return view;
		}
		MpTicketEntity ticket = this.ticketService.queryObject(id);
		Long currentUnixTime = DateUtils.getCurrentUnixTime();
		// 此门票不准购票
		if (ticket == null || ticket.getStatus() != 1
				|| ticket.getSaleDate() > currentUnixTime
				|| ticket.getEndBuyDate() < currentUnixTime) {
			view.setViewName("/wx/404.ftl");
		} else {
			String one; // 显示明天或者""
			String oneDate;// 明天的日期
			Double onePrice; // 明天购票的价格
			String two; // 显示后天或者""
			String twoDate;// 后天的日期
			Double twoPrice;// 后天购票的价格
			long startDate; // 开始购票日期
			long endDate; // 结束购票日期
			Calendar instance = Calendar.getInstance();
			// 开始购票时间和结束购票时间相差的天数(前台显示的插件要用到)
			int daysBetween = DateUtils.daysBetween(
					JoeyUtil.fomartDate(ticket.getStartBuyDate() * 1000)
							.compareTo(new Date()) >= 0 ? JoeyUtil
							.fomartDate(ticket.getStartBuyDate() * 1000)
							: new Date(), JoeyUtil.fomartDate(ticket
							.getEndBuyDate() * 1000));
			if (currentUnixTime > ticket.getStartBuyDate()) { // 包括明天和后天的票
				if (daysBetween <= 1) { // 只有今天一天的票可以购票
					one = "明天";
					two = "不能购买";
				} else {
					one = "明天";
					two = "后天";
				}
				instance.add(Calendar.DATE, 1);
			} else { // 预售票
				one = "";
				two = "";
				// 设置开始时间
				instance.setTime(JoeyUtil.fomartDate(ticket.getStartBuyDate() * 1000));
			}
			int count;
			if (daysBetween < 30) {
				count = 2;
			} else if (daysBetween < 60) {
				count = 3;
			} else {
				count = 4;
			}
			startDate = instance.getTime().getTime();
			if (90 <= daysBetween) { // 只能够买90天以内的
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(JoeyUtil.fomartDate(startDate));
				tempTime.add(Calendar.DATE, 89);
				endDate = tempTime.getTime().getTime();
			} else {
				endDate = ticket.getEndBuyDate() * 1000;
			}
			// 周末票价是否另算
			if (ticket.getWeekendType()) {
				onePrice = DateUtils.isWeekend(instance.getTime()) ? ticket
						.getWeekendPrice() : ticket.getPrice();
				oneDate = DateUtils.format(instance.getTime(), "MM-dd");
				instance.add(Calendar.DATE, 1);
				twoDate = DateUtils.format(instance.getTime(), "MM-dd");
				twoPrice = DateUtils.isWeekend(instance.getTime()) ? ticket
						.getWeekendPrice() : ticket.getPrice();
			} else {
				onePrice = ticket.getPrice();
				oneDate = DateUtils.format(instance.getTime(), "MM-dd");
				instance.add(Calendar.DATE, 1);
				twoDate = DateUtils.format(instance.getTime(), "MM-dd");
				twoPrice = ticket.getPrice();
			}

			Map<String, Object> map = new HashMap<>();
			map.put("memberId", getMember(request).getId());
			map.put("nowTime", DateUtils.getCurrentUnixTime());
			List<MpCashCouponHelpEntity> cashList = mpCashCouponHelpService
					.queryList(map);

			view.addObject("cashList", cashList);
			view.addObject("ticket", ticket);

			view.addObject("one", one);
			view.addObject("onePrice", onePrice);
			view.addObject("oneDate", oneDate);
			view.addObject("two", two);
			view.addObject("twoPrice", twoPrice);
			view.addObject("twoDate", twoDate);
			view.addObject("ticket", ticket);
			view.addObject("startDate", startDate);
			view.addObject("endDate", endDate);
			view.addObject("count", count);
			view.setViewName("/wx/ticket/godo.ftl");
		}
		return view;
	}

	/**
	 * 门票相对应的日期和价格
	 * 
	 * @param id
	 *            门票ID
	 * @return Map
	 */
	@RequestMapping("price")
	@ResponseBody
	public R price(Long id,HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpTicketEntity ticket = this.ticketService.queryObject(id);
		Long currentUnixTime = DateUtils.getCurrentUnixTime();
		if (ticket == null || ticket.getStatus() != 1
				|| ticket.getSaleDate() > currentUnixTime
				|| ticket.getEndBuyDate() < currentUnixTime) {
			return R.error("非法访问");
		} else {
			Calendar ca = Calendar.getInstance();
			Date startBuyDate = JoeyUtil.fomartDate(
					ticket.getStartBuyDate() * 1000).compareTo(new Date()) >= 0 ? JoeyUtil
					.fomartDate(ticket.getStartBuyDate() * 1000) : new Date();
			Date endBuyDate = JoeyUtil
					.fomartDate(ticket.getEndBuyDate() * 1000);
			logger.info("\n开始购票日期:" + DateUtils.format(startBuyDate)
					+ "，结束购票日期:" + DateUtils.format(endBuyDate));
			Map<String, Object> map = new HashMap<String, Object>();
			String format;
			Double price;
			ca.setTime(startBuyDate);
			String format2 = DateUtils.format(new Date());
			while (startBuyDate.compareTo(endBuyDate) <= 0) {
				format = DateUtils.format(startBuyDate);
				if (format.equals(format2)) { // 过滤掉今天的票
					// 业务处理...
					ca.add(Calendar.DATE, 1);
					startBuyDate = ca.getTime();
					continue;
				}
				if (ticket.getWeekendType()
						&& DateUtils.isWeekend(startBuyDate)) {
					price = ticket.getWeekendPrice();
				} else {
					price = ticket.getPrice();
				}
				map.put(format, price);
				// 业务处理...
				ca.add(Calendar.DATE, 1);
				startBuyDate = ca.getTime();
			}
			return R.ok().put("dayPricemMap", map);
		}
	}

}
