package com.hqc.controller.wx;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.hqc.entity.MpMemberEntity;
import com.hqc.util.CookieUtil;
import com.hqc.util.RRException;
import com.hqc.util.wx.RandomCardNumberUtil;
import com.hqc.util.wx.WxUtil;

/**
 * 微信授权控制器
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月1日
 * 
 */
abstract class WxAuthController extends BaseController {

	/**
	 * (登录验证)继承了此类的控制器优先调用此方法
	 * 
	 * @throws IOException
	 */
	@ModelAttribute
	protected void preRunLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (isLogin(request)) { // 登录了放行
			logger.info("preRunLogin");
		} else {
			// 判断Cookie是否有值
			String openId = CookieUtil.getCookie(request,
					WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY);
			String unionId = CookieUtil.getCookie(request,
					WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY);
			// 已经登陆过且Cookie有值
			if (null != openId && null != unionId) {
				logger.info("已经登陆过且Cookie有值,放行");
				request.getSession().setAttribute(
						WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY, openId); // 把授权的OPENID存入Session
				request.getSession().setAttribute(
						WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY, unionId); // 把授权的UNIONID存入Session
				MpMemberEntity member = mpMemberService.queryByOpenId(openId);
				if (member != null) { // 已经是登录的状态
					updateMember(request, member); // 更新Session用户
					logger.info("微信用户Cookie登录");
				} else { // 现在是进行注册
					MpMemberEntity mpMember = new MpMemberEntity();
					mpMember.setPassword(""); // 登录密码
					mpMember.setPhone(null); // 注册手机号
					mpMember.setOpenId(openId);
					mpMember.setUnionid(unionId);
					String generate = RandomCardNumberUtil.generateCardNum();
					boolean flag = true;
					while (flag) {
						if (mpMemberService.queryByCardNo(generate) != null) {
							generate = RandomCardNumberUtil.generateCardNum(); // 已存在此会员卡号，重新生成
						} else {
							flag = false;
						}
					}
					mpMember.setCardNo(generate); // 会员卡号
					mpMember.setStatus(1); // 1、启用 2、禁用
					mpMember.setVipLevel(Integer.valueOf(mpVipLevelService
							.getMin().getId().toString())); // 会员级别，关联会员级别表ID
					mpMember.setIntegral(0L); // 用户当前总积分
					mpMember.setLastYearIntegral(0L); // 上一年度积分
					mpMember.setCurrentYearIntegral(0L); // 本年度积分
					mpMemberService.save(mpMember);
					request.getSession().setAttribute(
							WxUtil.MP_MEMBER_LOGIN_SESSION_KEY, member);// 更新Session用户
					logger.info("微信用户Cookie自动注册");
				}
				return;
			}
			if (isAjax(request)) {
				throw new RRException("未登录,请先登录", 100); // 返回码为100为重新登录
			} else {
				String oauth2buildAuthorizationUrl = getoauthUrl(request,
						request.getServletPath());
				response.sendRedirect(oauth2buildAuthorizationUrl);
				return;
			}
		}
	}

}
