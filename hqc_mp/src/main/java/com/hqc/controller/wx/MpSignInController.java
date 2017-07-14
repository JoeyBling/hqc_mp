package com.hqc.controller.wx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpSignInEntity;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpSignInService;
import com.hqc.service.MpVipLevelService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.R;

@RestController
@RequestMapping("wx/user/sign")
public class MpSignInController extends WxAuthController {
	@Resource
	private MpSignInService mpSignInService;
	@Resource
	private MpMemberService memberService;
	@Resource
	private MpVipLevelService levelService;
	@RequestMapping("/selectSign")
	public R selectSign(HttpServletRequest request)
			throws NumberFormatException, ParseException {
		MpMemberEntity memberEntity = getMember(request);
		if (memberEntity == null) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
		Map<String, Object> maps=new HashMap<>();
		maps.put("memberId", memberEntity.getId());
		maps.put("date", JoeyUtil.stampDate(format.parse(str.toString()),DateUtils.DATE_TIME_PATTERN));
		List<MpSignInEntity> list = mpSignInService
				.queryByMemberId(maps);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberEntity.getId());
		map.put("createTime",
				JoeyUtil.stampDate(new Date(), DateUtils.DATE_PATTERN));
		int result = 0;
		try {
			result = mpSignInService.queryHasSign(map);// 查询该会员当天是否签到,没有签到返回0，签到了则返回1
		} catch (Exception e) {
			return R.ok().put("signList", list).put("result", result);
		}
		return R.ok().put("signList", list).put("result", result);
	}

	@RequestMapping("/doSign")
	@Transactional
	public R doSign(HttpServletRequest request) throws NumberFormatException,
			ParseException {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",getMember(request).getId());
		map.put("createTime",
				JoeyUtil.stampDate(new Date(), DateUtils.DATE_PATTERN));
		int count = 0;
		try {
			count = mpSignInService.queryHasSign(map);// 查询该会员当天是否签到,没有签到返回0，签到了则返回1
			if(count==1){
				return R.error("您今天已经签到过");
			}
		} catch (Exception e) {
			
		}
		MpSignInEntity entity = new MpSignInEntity();
		boolean isLogin = isLogin(request);
		if (isLogin == false) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		entity.setMemberId(getMember(request).getId());
		entity.setCreateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_PATTERN));
		mpSignInService.save(entity);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 获取当前时间为星期几(0:星期日，1星期一，2星期二。。。。)
		if (w < 0) {
			w = 0;
		}
		MpMemberEntity memberEntity = memberService
				.queryMpMemberInfoId(getMember(request).getId());
		MpMemberEntity member = new MpMemberEntity();
		Long currentIntegral = memberEntity.getCurrentYearIntegral();
		Long lastYearIntegral = memberEntity.getLastYearIntegral();
		Long integral = memberEntity.getIntegral();
	//	System.out.println("-------------"+integral);
		int result = 0;// 获取签到增加的积分，传到页面显示
		if (currentIntegral == null) {
			currentIntegral = Long.valueOf("0");
		}
		if (integral == null) {
			integral = Long.valueOf("0");
		}
		if (w == 0) {
			currentIntegral = currentIntegral + 10;
			member.setIntegral(integral + 10);
			result = 10;
		} else {
			currentIntegral = currentIntegral + 5;
			member.setIntegral(integral + 5);
			result = 5;
		}
		if (lastYearIntegral == null) {
			lastYearIntegral = Long.valueOf("0");
		}
	//	System.out.println("-------------"+member.getIntegral());
	  //  MpVipLevelEntity level=	levelService.queryLevel(member.getIntegral());
		member.setAvatar(memberEntity.getAvatar());
		member.setBirthday(memberEntity.getBirthday());
		member.setCardNo(memberEntity.getCardNo());
		member.setCreateTime(memberEntity.getCreateTime());
		member.setId(memberEntity.getId());
		member.setLastYearIntegral(memberEntity.getLastYearIntegral());
		member.setUnionid(memberEntity.getUnionid());
		member.setNickName(memberEntity.getNickName());
		member.setOpenId(memberEntity.getOpenId());
		member.setPassword(memberEntity.getPassword());
		member.setStatus(memberEntity.getStatus());
		member.setTrueName(memberEntity.getTrueName());
		member.setUpdateTime(memberEntity.getUpdateTime());
		member.setVipLevel(memberEntity.getVipLevel());
		member.setCurrentYearIntegral(currentIntegral);
		mpSignInService.update(member);
		return R.ok().put("result", result);
	}
}
