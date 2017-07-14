package com.hqc.controller.wx;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.hqc.entity.MpMemberEntity;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpVipLevelService;
import com.hqc.service.impl.WxServiceImpl;
import com.hqc.util.CookieUtil;
import com.hqc.util.wx.GetIpAddress;
import com.hqc.util.wx.RandomCardNumberUtil;
import com.hqc.util.wx.Sign;
import com.hqc.util.wx.WxUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 微信服务核心控制器
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月12日
 * 
 */
@Controller
@RequestMapping("/wx")
public class WxController extends BaseController {
	@Resource
	private MpMemberService mpMemberService;
	@Resource
	private MpVipLevelService mpVipLevelService;
	@Resource
	protected WxMpConfigStorage configStorage;

	@Resource
	protected WxMpService wxMpService;

	@Resource
	protected WxServiceImpl wxService;

	/**
	 * 微信公众号webservice主服务接口，提供与微信服务器的信息交互
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "core")
	public void wechatCore(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String timestamp = request.getParameter("timestamp");

		if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
			// 消息签名不正确，说明不是公众平台发过来的消息
			response.getWriter().println("非法请求");
			return;
		}

		String echoStr = request.getParameter("echostr");
		if (StringUtils.isNotBlank(echoStr)) {
			// 说明是一个仅仅用来验证的请求，回显echostr
			String echoStrOut = String.copyValueOf(echoStr.toCharArray());
			response.getWriter().println(echoStrOut);
			return;
		}

		String encryptType = StringUtils.isBlank(request
				.getParameter("encrypt_type")) ? "raw" : request
				.getParameter("encrypt_type");

		if ("raw".equals(encryptType)) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request
					.getInputStream());
			logger.info("\n明文传输的消息：\n" + inMessage.toString());
			WxMpXmlOutMessage outMessage = wxService.execute(inMessage);

			if (outMessage != null) {
				logger.info(outMessage.toString());
				response.getWriter().write(outMessage.toXml());
			} else {
				response.getWriter().write("");
			}
			return;
		}

		if ("aes".equals(encryptType)) {
			// 是aes加密的消息
			String msgSignature = request.getParameter("msg_signature");
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
					request.getInputStream(), configStorage, timestamp, nonce,
					msgSignature);
			logger.info("\n消息解密后内容为：\n" + inMessage.toString());
			WxMpXmlOutMessage outMessage = wxService.execute(inMessage);
			logger.info(outMessage.toString());
			response.getWriter()
					.write(outMessage.toEncryptedXml(configStorage));
			return;
		}

		response.getWriter().println("不可识别的加密类型");
		return;
	}

	/**
	 * 微信网页授权回调
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value = "oauth")
	public void oauth2(String code, String state, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getServletContext().getContextPath();
		logger.info(state);
		// 获得access token
		if (StringUtils.isBlank(code)) {
			response.sendRedirect(contextPath + "/wx/404.html");
			return;
		}
		logger.info(code);
		// 获得用户基本信息
		WxMpUser wxMpUser = null;
		// 验证access token
		boolean valid = false;
		try {
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService
					.oauth2getAccessToken(code);

			wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,
					null);

			logger.info(wxMpUser);
			// 刷新access token
			wxMpOAuth2AccessToken = wxMpService
					.oauth2refreshAccessToken(wxMpOAuth2AccessToken
							.getRefreshToken());

			valid = wxMpService
					.oauth2validateAccessToken(wxMpOAuth2AccessToken);
		} catch (Exception e) {
			response.sendRedirect(contextPath + "/wx/404.html");
			return;
		}
		if (!valid) {
			response.sendRedirect(contextPath + "/wx/404.html"); // 微信验证未通过，非法访问
			return;
		}
		// 登录成功
		String openId = wxMpUser.getOpenId();
		String unionId = wxMpUser.getUnionId();
		logger.info("登录成功openId:" + openId + ",unionId:"
				+ wxMpUser.getUnionId());

		// 测试发送模版消息
		try {
			WxMpTemplateMsgService templateMsgService = wxMpService
					.getTemplateMsgService();
			String addTemplate = templateMsgService
					.addTemplate("OPENTM202199104");
			if (null == addTemplate) {
				logger.info("请在公众号添加模板消息 编号为 OPENTM202199104 标题为   购买成功通知 的模板!");
			} else {
				WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
				templateMessage.setToUser(openId);
				templateMessage
						.setTemplateId("HqGyZ5EQmyl3hEwA4Z-VWmdCp6zCP3IRWSAoa57afzo");
				templateMessage.setUrl("https://www.baidu.com/");
				templateMessage.getData().add(
						new WxMpTemplateData("first",
								"尊敬的13237510567用户： 您兑换的【天门门票】兑换成功", "#173177"));
				templateMessage.getData().add(
						new WxMpTemplateData("keyword1", "商品名称", "#173177"));
				templateMessage.getData().add(
						new WxMpTemplateData("keyword2", "有效期", "#173177"));
				templateMessage.getData().add(
						new WxMpTemplateData("keyword3", "验证码", "#173177"));
				templateMessage.getData().add(
						new WxMpTemplateData("remark", "请到东部华侨城出示此消息进行使用",
								"#173177"));
				templateMsgService.sendTemplateMsg(templateMessage);
			}
		} catch (Exception e) {
			// 进行处理
			logger.info("推送模版消息失败!" +  e.getMessage());
		}

		// 添加Cookie(30分钟有效期)
		CookieUtil.addCookie(WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY, openId,
				null, 1800, response);
		CookieUtil.addCookie(WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY,
				unionId, null, 1800, response);
		// WxMpUser userInfo = wxService.getUserInfo(openId, null);
		request.getSession().setAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_OPENID_KEY, openId); // 把授权的OPENID存入Session
		request.getSession().setAttribute(
				WxUtil.MP_MEMBER_LOGIN_SESSION_UNIONID_KEY, unionId); // 把授权的UNIONID存入Session
		MpMemberEntity member = mpMemberService.queryByOpenId(openId);
		logger.info(GetIpAddress.getIpAddress(request));
		if (member != null) { // 已经是登录的状态
			updateMember(request, member); // 更新Session用户
			logger.info("微信用户登录");
			// if (null != state) {
			// response.sendRedirect(contextPath + state);
			// return;
			// } else {
			response.sendRedirect(contextPath + "/wx/user/userCenter.html");
			return;
			// }
		} else { // 现在是进行注册
			// response.sendRedirect(contextPath + "/wx/user/login.html");

			MpMemberEntity mpMember = new MpMemberEntity();
			mpMember.setPassword(""); // 登录密码
			mpMember.setPhone(null); // 注册手机号
			mpMember.setOpenId(openId);
			mpMember.setUnionid(unionId);
			mpMember.setNickName(wxMpUser.getNickname());
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
			mpMember.setVipLevel(Integer.valueOf(mpVipLevelService.getMin()
					.getId().toString())); // 会员级别，关联会员级别表ID
			mpMember.setIntegral(0L); // 用户当前总积分
			mpMember.setLastYearIntegral(0L); // 上一年度积分
			mpMember.setCurrentYearIntegral(0L); // 本年度积分
			mpMemberService.save(mpMember);
			request.getSession().setAttribute(
					WxUtil.MP_MEMBER_LOGIN_SESSION_KEY, member);// 更新Session用户
			logger.info("微信用户授权自动注册");
			response.sendRedirect(contextPath + "/wx/user/userCenter.html");
			return;
		}
	}

	/**
	 * 跳转授权登录页面
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value = "oauthLogin")
	public void oauthLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String oauth2buildAuthorizationUrl = getoauthUrl(request, null);
		response.sendRedirect(oauth2buildAuthorizationUrl);
		return;
	}

	/**
	 * 微信WIFI连接Portal回调
	 * 
	 * @param extend
	 *            为上文中调用呼起微信JSAPI时传递的extend参数，这里原样回传给商家主页
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return String
	 */
	@RequestMapping(value = "wifi")
	public String wifi(String extend, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(extend);
		logger.info(request.getRemoteAddr());
		return "/wx/index.ftl";
	}

	@RequestMapping("/getConfig")
	public void getConfig(String url, Model model, HttpServletResponse response)
			throws IOException, WxErrorException, NoSuchAlgorithmException {
		System.out.println("----------------------");
		String jsapi_ticket = wxMpService.getJsapiTicket();
		String result;
		if ("".equals(jsapi_ticket)) {
			result = "error";
		}
		// 进行数据的加密(url,jsapi_ticket,nonceStr,timestamp)等参数进行SHA1加密
		Map<String, String> ret = Sign.sign(jsapi_ticket, url);
		String appId = wxMpService.getWxMpConfigStorage().getAppId();
		String timestamp = ret.get("timestamp");
		String nonceStr = ret.get("nonceStr");
		String signature = ret.get("signature");
		result = "success";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("timestamp", timestamp);
		jsonObject.put("nonceStr", nonceStr);
		jsonObject.put("signature", signature);
		jsonObject.put("result", result);
		jsonObject.put("appId", appId);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().write(jsonObject.toString());
	}

	/**
	 * 404页面
	 * 
	 * @return
	 */
	@RequestMapping("/*")
	public String noPageFind() {
		return "/wx/404.ftl";
	}

	/**
	 * 404页面
	 * 
	 * @return
	 */
	@RequestMapping("/*/*")
	public String notPageFind() {
		return "/wx/404.ftl";
	}
}
