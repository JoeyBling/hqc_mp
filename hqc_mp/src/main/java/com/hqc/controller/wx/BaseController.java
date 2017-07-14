package com.hqc.controller.wx;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;

import org.apache.log4j.Logger;

import com.hqc.entity.MpMemberEntity;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpVipLevelService;
import com.hqc.util.Constant;
import com.hqc.util.RedisUtil;
import com.hqc.util.wx.WxUtil;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
abstract class BaseController {
	/**
	 * 微信API的Service
	 */
	@Resource
	protected WxMpService wxMpService;

	@Resource
	protected MpMemberService mpMemberService;
	@Resource
	protected MpVipLevelService mpVipLevelService;
	/**
	 * Slf日志
	 */
	protected final Logger logger = Logger.getLogger(getClass());

	/** Redis工具类 */
	@Resource
	protected RedisUtil redisUtil;

	/**
	 * 判断前台会员是否登录，未登录跳转网页授权
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
	protected boolean isLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_KEY) != null;
	}

	/**
	 * 获取会员
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return MpMemberEntity
	 */
	protected MpMemberEntity getMember(HttpServletRequest request) {
		return (MpMemberEntity) request.getSession().getAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_KEY);
	}

	/**
	 * 更新Session用户
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param entity
	 *            MpMemberEntity
	 */
	protected void updateMember(HttpServletRequest request,
			MpMemberEntity entity) {
		request.getSession()
				.removeAttribute(WxUtil.MP_MEMBER_LOGIN_SESSION_KEY);
		request.getSession().setAttribute(WxUtil.MP_MEMBER_LOGIN_SESSION_KEY,
				entity);
	}

	/**
	 * 获取微信授权登录的OpenID
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Object
	 */
	protected Object getSessionOpenId(HttpServletRequest request) {
		return request.getSession().getAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY);
	}

	/**
	 * 获取微信授权登录的OpenID
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Object
	 */
	protected Object getSessionUnionid(HttpServletRequest request) {
		return request.getSession().getAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY);
	}

	/**
	 * 判断是否是 Ajax 请求
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return boolean
	 */
	protected boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	/**
	 * 获取微信授权地址
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param state
	 *            回调URL附带的参数
	 * @return 微信授权地址
	 */
	protected String getoauthUrl(HttpServletRequest request, String state) {
		String oauth2buildAuthorizationUrl = wxMpService
				.oauth2buildAuthorizationUrl(Constant.getNetAddress()
						+ "wx/oauth", WxConsts.OAUTH2_SCOPE_USER_INFO, state);
		logger.info(oauth2buildAuthorizationUrl);
		return oauth2buildAuthorizationUrl;
	}
}
