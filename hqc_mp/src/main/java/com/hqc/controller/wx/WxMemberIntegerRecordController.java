package com.hqc.controller.wx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.service.WxMemberIntegerRecordService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
/**
 * 微信端我的积分
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/wx/user/memberIntegralrecord")
public class WxMemberIntegerRecordController extends BaseController{
	@Resource
	private WxMemberIntegerRecordService wxMemberIntegerRecordService;
	/**
	 * 列表
	 * @param page
	 * @param goodsType
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryList")
	public R queryLiset(Integer page, 
			HttpServletRequest request) {
		Integer limit = 10;
		if (isLogin(request) == false) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpMemberEntity member = getMember(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", member.getId());
		map.put("limit", limit);
		map.put("offset", (page - 1) * limit);
		List<MpIntegralRecordEntity> list=wxMemberIntegerRecordService.queryList(map);
		int total=wxMemberIntegerRecordService.queryTotal(map);
		PageUtils pageutil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageutil);
	}

}
