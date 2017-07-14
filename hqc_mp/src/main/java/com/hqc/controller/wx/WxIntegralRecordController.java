package com.hqc.controller.wx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpMemberEntity;
import com.hqc.service.GoodsExchangeService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

@RestController
@RequestMapping("/wx/user/mpintegralrecord")
public class WxIntegralRecordController extends WxAuthController {
	@Resource
	private GoodsExchangeService exchangeService;

	/**
	 * 获取用户积分商品兑换记录
	 * 
	 * @param page
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/queryList")
	public R queryLiset(Integer page, Integer goodsType,
			HttpServletRequest request) throws NumberFormatException, ParseException {
		Integer limit = 10;
		if (isLogin(request) == false) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		 Calendar c = Calendar.getInstance();
		 c.add(Calendar.MONTH,-6);
		 System.out.println(c.getTime());
		 System.out.println(JoeyUtil.stampDate(c.getTime(),DateUtils.DATE_TIME_PATTERN));
		MpMemberEntity member = getMember(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", member.getId());
		map.put("goodsType", goodsType);
		map.put("time", JoeyUtil.stampDate(c.getTime(),DateUtils.DATE_TIME_PATTERN));
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		List<Map<String, Object>> list = exchangeService.queryByMemberId(map);
		int total = exchangeService.recordTotal(map);
		PageUtils pageutil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageutil);
	}

}
