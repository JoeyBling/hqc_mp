package com.hqc.ws;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.hqc.entity.MpBannerEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpTicketEntity;
import com.hqc.entity.MpTicketOrderEntity;
import com.hqc.service.MpBannerService;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpOrderRecordsService;
import com.hqc.service.MpTicketOrderService;
import com.hqc.service.MpTicketService;
import com.hqc.util.Constant;
import com.hqc.util.DateUtils;
import com.hqc.util.HttpUtil;
import com.hqc.util.JoeyUtil;
import com.hqc.util.OrderNoUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 微信门票WS服务
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月6日
 * 
 */
@Controller
@RequestMapping("/ws")
public class WxTicketWSImpl extends BaseWs implements WxTicketWS {

	@Resource
	private MpBannerService mpBannerService;
	@Resource
	private MpTicketService mpTicketService;
	@Resource
	private MpTicketOrderService mpTicketOrderService;
	@Resource
	private MpMemberService mpMemberService;
	@Resource
	private MpOrderRecordsService mpOrderRecordsService;

	@Override
	@ResponseBody
	@RequestMapping(value = "/decodeUserInfo")
	public Map<String, Object> decodeUserInfo(String encryptedData, String iv,
			String code, HttpServletRequest request) {
		SortedMap<Object, Object> packageParams = HttpUtil.getParams(request);
		// 参数校验
		if (!validateParameter(packageParams, "encryptedData", "iv", "code")) {
			return R.error("参数格式不正确!");
		}
		Map<String, Object> decodeUserInfo = wxMiniUtil.decodeUserInfo(
				encryptedData, iv, code);
		logger.info(decodeUserInfo);
		return decodeUserInfo;
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/banner")
	public Object banner(Integer page, Integer limit, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != page && null != limit) {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
		}
		// 查询轮播图状态为启用的
		map.put("status", 1);
		// fastjson过滤字段
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(
				MpBannerEntity.class, "title", "thumbUrl", "url");
		return R.ok()
				.put("banner",
						JSONObject.toJSONString(mpBannerService.queryList(map),
								filter));
	}

	@Override
	@ResponseBody
	@RequestMapping(value = "/ticket")
	public Object ticket(Long ticketId, Integer page, Integer limit,
			HttpServletRequest request) {
		// 暂时过滤掉这些字段
		final String[] arr = new String[] { "ticketNo", "saleDate", "status",
				"updateTime", "createTime" };
		PropertyFilter propertyFilter = new PropertyFilter() {
			public boolean apply(Object object, String name, Object value) {
				for (String string : arr) {
					if (name.equalsIgnoreCase(string)) {
						return false;// 过滤掉
					}
				}
				return true;// 不过滤
			}
		};
		Long currentUnixTime = DateUtils.getCurrentUnixTime();
		if (ticketId != null) { // 查询单个门票信息
			MpTicketEntity queryObject = mpTicketService.queryObject(ticketId);
			// 为空或者未到开售日期或者已经过了最后购票日期
			if (queryObject == null || queryObject.getStatus() != 1
					|| queryObject.getSaleDate() > currentUnixTime
					|| queryObject.getEndBuyDate() < currentUnixTime) {
				return R.error("不存在此门票Id");
			}
			queryObject.setThumbUrl(Constant.getNetAddress()
					+ queryObject.getThumbUrl());
			return R.ok().put("entity",
					JSONObject.toJSONString(queryObject, propertyFilter));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != page && null != limit) {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
		}
		// 上架的
		map.put("status", 1);
		map.put("saleDate", currentUnixTime);
		map.put("endBuyDate", currentUnixTime);
		List<MpTicketEntity> queryList = mpTicketService.queryList(map);
		for (MpTicketEntity mpTicketEntity : queryList) {
			// 图片地址加上HTTP
			mpTicketEntity.setThumbUrl(Constant.getNetAddress()
					+ mpTicketEntity.getThumbUrl());
		}
		int total = mpTicketService.queryTotal(map);
		return R.ok().put(
				"page",
				JSONObject.toJSONString(
						new PageUtils(queryList, total, limit == null ? total
								: limit, page == null ? 1 : page),
						propertyFilter));
	}

	@Override
	@ResponseBody
	@RequestMapping("/order")
	public Object order(String encryptedData, String iv, String code,
			String orderNo, Integer type, Integer page, Integer limit,
			HttpServletRequest request) {
		SortedMap<Object, Object> packageParams = HttpUtil.getParams(request);
		// 参数校验
		if (!validateParameter(packageParams, "encryptedData", "iv", "code")) {
			return R.error("参数格式不正确!");
		}
		Map<String, Object> decodeUserInfo = wxMiniUtil.decodeUserInfo(
				encryptedData, iv, code);
		if (decodeUserInfo.get("code") == null
				|| Integer.valueOf(decodeUserInfo.get("code").toString()) != 0) {
			return decodeUserInfo;
		}
		logger.info("小程序获取并解密用户数据:" + decodeUserInfo);
		// 获取用户unionid
		String unionid = decodeUserInfo.get("unionId").toString();
		// 暂时过滤掉这些字段
		final String[] arr = new String[] { "id", "saleDate", "ticketNo",
				"mpMemberEntity", "updateTime" };
		PropertyFilter propertyFilter = new PropertyFilter() {
			public boolean apply(Object object, String name, Object value) {
				for (String string : arr) {
					if (name.equalsIgnoreCase(string)) {
						return false;// 过滤掉
					}
				}
				return true;// 不过滤
			}
		};

		if (orderNo != null) { // 查询单个订单信息
			MpTicketOrderEntity queryByOrderNo = mpTicketOrderService
					.queryByOrderNo(orderNo, unionid);
			// 不存在或者已删除
			if (null == queryByOrderNo || queryByOrderNo.getDel()) {
				return R.error("不存在此订单Id");
			}
			queryByOrderNo.getMpTicketEntity().setThumbUrl(
					Constant.getNetAddress()
							+ queryByOrderNo.getMpTicketEntity().getThumbUrl());
			return R.ok().put("entity",
					JSONObject.toJSONString(queryByOrderNo, propertyFilter));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		if (null != page && null != limit) {
			map.put("offset", (page - 1) * limit);
			map.put("limit", limit);
		}
		// 公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
		map.put("unionid", unionid);
		map.put("type", type);
		// 显示未删除的
		map.put("del", 0);
		// 时间降序
		map.put("orderby", "o.`status` = 3 DESC,o.`create_time` DESC");
		List<MpTicketOrderEntity> queryList = mpTicketOrderService
				.queryList(map);
		for (MpTicketOrderEntity mpTicketOrderEntity : queryList) {
			// 图片地址加上HTTP
			mpTicketOrderEntity.getMpTicketEntity().setThumbUrl(
					Constant.getNetAddress()
							+ mpTicketOrderEntity.getMpTicketEntity()
									.getThumbUrl());
		}
		int total = mpTicketOrderService.queryTotal(map);
		return R.ok().put(
				"page",
				JSONObject.toJSONString(
						new PageUtils(queryList, total, limit == null ? total
								: limit, page == null ? 1 : page),
						propertyFilter));
	}

	@Override
	@ResponseBody
	@RequestMapping("/canOrder")
	public Object canOrder(String encryptedData, String iv, String code,
			String orderNo, HttpServletRequest request) {
		SortedMap<Object, Object> packageParams = HttpUtil.getParams(request);
		// 参数校验
		if (!validateParameter(packageParams, "encryptedData", "iv", "code",
				"orderNo")) {
			return R.error("参数格式不正确!");
		}
		Map<String, Object> decodeUserInfo = wxMiniUtil.decodeUserInfo(
				encryptedData, iv, code);
		if (decodeUserInfo.get("code") == null
				|| Integer.valueOf(decodeUserInfo.get("code").toString()) != 0) {
			return decodeUserInfo;
		}
		logger.info("小程序获取并解密用户数据:" + decodeUserInfo);
		// 获取用户unionid
		String unionid = decodeUserInfo.get("unionId").toString();
		int update = mpTicketOrderService.updateByWhere(orderNo, unionid, 4);
		if (update > 0) {
			return R.ok();
		} else {
			return R.error("更新失败,请检查订单编号或unionid");
		}
	}

	@Override
	@ResponseBody
	@RequestMapping("/delOrder")
	public Object delOrder(String encryptedData, String iv, String code,
			String orderNo, HttpServletRequest request) {
		SortedMap<Object, Object> packageParams = HttpUtil.getParams(request);
		// 参数校验
		if (!validateParameter(packageParams, "encryptedData", "iv", "code",
				"orderNo")) {
			return R.error("参数格式不正确!");
		}
		Map<String, Object> decodeUserInfo = wxMiniUtil.decodeUserInfo(
				encryptedData, iv, code);
		if (decodeUserInfo.get("code") == null
				|| Integer.valueOf(decodeUserInfo.get("code").toString()) != 0) {
			return decodeUserInfo;
		}
		logger.info("小程序获取并解密用户数据:" + decodeUserInfo);
		// 获取用户unionid
		String unionid = decodeUserInfo.get("unionId").toString();
		int update = mpTicketOrderService.deleteByWhere(orderNo, unionid);
		if (update > 0) {
			return R.ok();
		} else {
			return R.error("删除失败,请检查订单编号或unionid");
		}
	}

	@Override
	@ResponseBody
	@RequestMapping("/createOrder")
	@Transactional
	public Object createOrder(String encryptedData, String iv, String code,
			Long ticketId, String orderPhone, String useTime, long ticketCount,
			HttpServletRequest request) throws Exception {
		SortedMap<Object, Object> packageParams = HttpUtil.getParams(request);
		// 参数校验
		if (!validateParameter(packageParams, "encryptedData", "iv", "code",
				"ticketId", "orderPhone", "useTime", "ticketCount")) {
			return R.error("参数格式不正确!");
		}
		if (DateUtils.parse(useTime).compareTo(JoeyUtil.getTimesByDay(1)) < 0) {
			return R.error("不能预定今天或者今天之前的票");
		}
		Map<String, Object> decodeUserInfo = wxMiniUtil.decodeUserInfo(
				encryptedData, iv, code);
		if (decodeUserInfo.get("code") == null
				|| Integer.valueOf(decodeUserInfo.get("code").toString()) != 0) {
			return decodeUserInfo;
		}
		logger.info("小程序获取并解密用户数据:" + decodeUserInfo);
		// 获取当前时间戳
		Long currentUnixTime = DateUtils.getCurrentUnixTime();
		MpTicketEntity queryObject = mpTicketService.queryObject(ticketId);
		// 为空或者未到开售日期或者已经过了最后购票日期
		if (null == queryObject || queryObject.getSaleDate() > currentUnixTime
				|| queryObject.getEndBuyDate() < currentUnixTime) {
			return R.error("不存在此门票Id");
		}
		// 获取用户unionid
		String unionid = decodeUserInfo.get("unionId").toString();

		// 此会员关注过公众号
		MpMemberEntity queryByUnionId = mpMemberService.queryByUnionId(unionid);
		Long memberId = queryByUnionId == null ? null : queryByUnionId.getId();

		// 添加购票订单记录
		MpTicketOrderEntity entity = new MpTicketOrderEntity();
		entity.setCreateTime(currentUnixTime);
		entity.setDel(false);
		entity.setStartTime(JoeyUtil.dateToStamp(useTime));
		entity.setEndTime(JoeyUtil.dateToStamp(useTime));
		// 订单号
		String orderNo = OrderNoUtils.getOrder_no();
		entity.setOrderNo(orderNo);
		entity.setOrderPhone(orderPhone);
		entity.setOrderTitle(queryObject.getTicketName());
		entity.setMemberId(memberId);
		// 待支付
		entity.setStatus(3);
		entity.setTicketCount(ticketCount);
		entity.setTicketId(queryObject.getId());
		entity.setUnionid(unionid);
		// 总金额
		Double totalFee;
		// 周末票价
		if (queryObject.getWeekendType() && DateUtils.isWeekend(new Date())) {
			totalFee = queryObject.getWeekendPrice() * ticketCount;
		} else { // 普通票价
			totalFee = queryObject.getPrice() * ticketCount;
		}
		entity.setTotalFee(totalFee);
		entity.setUpdateTime(currentUnixTime);
		// 购票订单记录结束

		// 商品订单记录
		// MpOrderRecordsEntity order = new MpOrderRecordsEntity();
		// order.setType(1); // 订单类型(1购票订单 2积分商城门票订单 3积分商城代金卷订单)
		// order.setTicketId(queryObject.getId());
		// order.setCreateTime(currentUnixTime);
		// order.setItemCount(ticketCount);
		// order.setOrderNo(orderNo);
		// order.setUpdateTime(currentUnixTime);

		// 下单没有支付
		// String itemCode =
		// RandomCardNumberUtil.credit_card_number(RandomCardNumberUtil.MASTERCARD_PREFIX_LIST,
		// 6, 1)[0];
		// logger.info("生成的6位随机取票验证码");
		// order.setItemCode(itemCode); //取票凭证码
		//
		// order.setMemberId(memberId);
		// order.setPhone(orderPhone);
		// order.setStatus(0); // 0、已下单 1、已兑换 2、已取票
		// // 商品订单记录结束
		// mpOrderRecordsService.save(order);
		mpTicketOrderService.save(entity);
		// 返回订单详情
		logger.info("微信小程序下单生成订单号:" + orderNo);
		// 暂时过滤掉这些字段
		final String[] arr = new String[] { "saleDate", "ticketNo",
				"mpMemberEntity", "updateTime" };
		PropertyFilter propertyFilter = new PropertyFilter() {
			public boolean apply(Object object, String name, Object value) {
				for (String string : arr) {
					if (name.equalsIgnoreCase(string)) {
						return false;// 过滤掉
					}
				}
				return true;// 不过滤
			}
		};
		return R.ok().put(
				"entity",
				JSONObject.toJSONString(
						mpTicketOrderService.queryObject(entity.getId()),
						propertyFilter));
	}

}
