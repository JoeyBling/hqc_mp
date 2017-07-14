package com.hqc.controller.wx;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hqc.entity.MpMemberEntity;
import com.hqc.service.MpMemberService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.R;
import com.hqc.util.wx.WxUtil;

/**
 * 微信用户相关视图
 * 
 * @author cxw
 * @date 2017年5月26日
 */
@RestController
@RequestMapping("/wx/member")
public class WxMemberController extends WxAuthController {

	@Resource
	private MpMemberService mpMemberService;

	/**
	 * 会员中心（个人信息展示）
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	public R info(HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpMemberEntity memberEntity = getMember(request);

		MpMemberEntity member = this.mpMemberService
				.queryMpMemberInfoId(memberEntity.getId());
		R r = R.ok().put("member", member);
		//如果当前积分比当前等级最大积分大，则可升级,标记("updateVipStatus", 1)
		if(member.getVipLevelEntity() != null){
			if(member.getVipLevelEntity().getMaxIntegral() !=null
					&& member.getVipLevelEntity().getMaxIntegral() >= member.getIntegral()){
				r.put("updateVipStatus", 0);
			}else{
				r.put("updateVipStatus", 1);
			}
		}else{
			r.put("updateVipStatus", 1);
		}
		return r;
	}

	/**
	 * 个人详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getView")
	public ModelAndView getView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		MpMemberEntity memberEntity = null;
		if (isLogin(request)) {
			memberEntity = getMember(request);
		} else {
			view.setViewName("/wx/user/login.ftl");
			return view;
		}
		// 获取用户和等级；
		MpMemberEntity member = this.mpMemberService
				.queryMpMemberInfoId(memberEntity.getId());

		// 格式化时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		Date createTime = JoeyUtil.fomartDate(member.getCreateTime() * 1000);
		String bd = null;
		if (member.getBirthday() != null) {
			Date birthday = JoeyUtil.fomartDate(member.getBirthday() * 1000);
			bd = sdf.format(birthday);
		}
		String ct = sdf.format(createTime);
		view.addObject("member", member);
		view.addObject("createTime", ct);
		view.addObject("birthday", bd);
		view.setViewName("/wx/user/userInfo.ftl");
		return view;
	}

	/**
	 * 修改用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public R update(HttpServletRequest request) {
		MpMemberEntity memberEntity = null;
		if (isLogin(request)) {
			memberEntity = getMember(request);
		} else {
			return R.error(100, "当前用户未登录，请重新登录！");
		}
		MpMemberEntity member = new MpMemberEntity();

		String userName = request.getParameter("trueName");
		if (!StringUtils.isBlank(userName)) {
			member.setTrueName(userName);
			memberEntity.setTrueName(userName);
		} else {
			return R.error("名字不能为空！");
		}
		member.setId(memberEntity.getId());
		member.setUpdateTime(DateUtils.getCurrentUnixTime());
		mpMemberService.update(member);
		updateMember(request, memberEntity); // 更新Session用户
		return R.ok();
	}

	/**
	 * 修改生日
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/birthday")
	public R birthday(String birthday, HttpServletRequest request)
			throws Exception {
		MpMemberEntity memberEntity = null;
		if (isLogin(request)) {
			memberEntity = getMember(request);
		} else {
			return R.error(100, "当前用户未登录，请重新登录！");
		}
		MpMemberEntity member = new MpMemberEntity();
		if (mpMemberService.queryMpMemberInfoId(memberEntity.getId())
				.getBirthday() != null) {
			return R.error("不能再次更改会员生日");
		}

		member.setId(memberEntity.getId());
		member.setBirthday(JoeyUtil.stampDate(DateUtils.parse(birthday),
				DateUtils.DATE_PATTERN));
		mpMemberService.update(member);
		memberEntity.setBirthday(JoeyUtil.stampDate(DateUtils.parse(birthday),
				DateUtils.DATE_PATTERN));
		updateMember(request, memberEntity); // 更新Session用户
		return R.ok();
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePassword")
	public R updatePassword(HttpServletRequest request) {

		MpMemberEntity memberEntity;
		if (isLogin(request)) {
			memberEntity = getMember(request);
		} else {
			return R.error(100, "当前用户未登录，请重新登录！");
		}

		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		String secPassword = request.getParameter("secPassword");

		if (StringUtils.isBlank(oldPassword)) {
			return R.error("请填写原密码");
		}
		if (StringUtils.isBlank(newPassword)) {
			return R.error("请填写新密码");
		}
		if (StringUtils.isBlank(secPassword)) {
			return R.error("请确认密码");
		}
		if (!newPassword.equals(secPassword)) {
			return R.error("两次密码输入不同");
		}
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", memberEntity.getId());
		map.put("oldPassword", new Sha256Hash(oldPassword).toHex());
		map.put("newPassword", new Sha256Hash(newPassword).toHex());

		int count = this.mpMemberService.updatePassword(map);
		if (count == 0) {
			return R.error("原密码错误");
		}
		request.getSession()
				.removeAttribute(WxUtil.MP_MEMBER_LOGIN_SESSION_KEY);
		return R.ok();
	}

	/**
	 * 验证原手机号码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/validateOldCode")
	public R validateOldCode(HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "当前用户未登录，请重新登录！");
		}
		MpMemberEntity member = getMember(request);
		String oldPhone = request.getParameter("oldPhone");
		String oldCode = request.getParameter("oldCode");
		if (StringUtils.isBlank(oldCode)) {
			return R.error("请输入验证码");
		}
		if(!oldPhone.equals(member.getPhone())){
			return R.error("原号码不对，请刷新页面");
		}
		String code = (String) request.getSession().getAttribute(
				WxUtil.MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY);
		if (StringUtils.isBlank(code)) {
			return R.error("请获取验证码");
		}
		if (!oldCode.equals(code)) {
			return R.error("验证码错误");
		}
		request.getSession().removeAttribute(
				WxUtil.MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY);
		// 清除验证码，添加验证成功标识
		request.getSession().removeAttribute(
				WxUtil.MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY);
		request.getSession().setAttribute(WxUtil.MP_MEMBER_OLD_PHONE_CODE_TRUE,
				WxUtil.MP_MEMBER_OLD_PHONE_CODE_TRUE);
		return R.ok();
	}

	/**
	 * 修改手机号码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePhone")
	public R updatePhone(HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "当前用户未登录，请重新登录！");
		}
		MpMemberEntity memberEntity = getMember(request);
		//n如果为0的话就是原号码没有，重新加入号码，不用验证原号码
		String n = request.getParameter("n");
		String newphone = request.getParameter("newphone");
		String newCode = request.getParameter("newCode");
		if (StringUtils.isBlank(newphone)) {
			return R.error("请输入手机号");
		}
		if (StringUtils.isBlank(newCode)) {
			return R.error("请输入验证码");
		}
		String code = (String) request.getSession().getAttribute(
				WxUtil.MP_MEMBER_NEW_PHONE_CODE_SESSION_KEY);
		String phone = (String) request.getSession().getAttribute(
				WxUtil.MP_MEMBER_NEW_PHONE_SESSION_KEY);
		String validate = (String) request.getSession().getAttribute(
				WxUtil.MP_MEMBER_OLD_PHONE_CODE_TRUE);
		
		if(!StringUtils.isBlank(memberEntity.getPhone())){
			if (validate == null || "".equals(validate)) {
				return R.error("原手机号未验证");
			}
		}
		
		if(!StringUtils.isBlank(n) && Integer.valueOf(n) == 0){
			
			}else{
				if (validate == null || "".equals(validate)) {
					return R.error("原手机号未验证");
			}
			}

		if (StringUtils.isBlank(code)) {
			return R.error("请获取验证码");
		}
		if (!newphone.equals(phone)) {
			return R.error("请对应手机号");
		}
		if (!code.equals(newCode)) {
			return R.error("验证码错误");
		}
		

		logger.info("\n原手机号码：" + memberEntity.getPhone());
		MpMemberEntity member = new MpMemberEntity();
		member.setId(memberEntity.getId());
		member.setPhone(phone);
		
		this.mpMemberService.update(member);
		memberEntity.setPhone(phone);
		logger.info("\n新手机号码：" + phone + "\n号码替换成功！\n时间："
				+ DateUtils.format(new Date()));
		updateMember(request, memberEntity); // 更新Session用户
		return R.ok();
	}
	/**
	 * 修改用户会员等级
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateVip")
	public R updateVip(HttpServletRequest request){
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		MpMemberEntity memberEntity = getMember(request);

		MpMemberEntity member = this.mpMemberService
				.updateVipLevel(memberEntity.getId());
		
		updateMember(request, memberEntity); // 更新Session用户
		return  R.ok().put("member", member);
	}
}

	
