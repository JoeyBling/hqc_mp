package com.hqc.controller.octopus;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.hqc.util.Constant;
import com.hqc.util.R;
import com.hqc.util.ShiroUtils;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
@RequestMapping("/octopus")
public class SysLoginController {

	@Resource
	private Producer producer;

	/**
	 * 默认访问页面
	 * 
	 * @return String
	 */
	@RequestMapping("/")
	public String redirect() {
		if (ShiroUtils.isLogin())
			return "/octopus/sys/index.ftl";
		else
			return "/octopus/sys/login.ftl";
	}

	/**
	 * 管理员验证码
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response) throws ServletException,
			IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	/**
	 * 管理员登录
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param captcha
	 *            验证码
	 * @return Map
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, Integer rememberUser,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (StringUtils.isBlank(username)) {
			return R.error("请输入用户名！");
		}
		if (StringUtils.isBlank(password)) {
			return R.error("请输入密码！");
		}
		try {
			Subject subject = ShiroUtils.getSubject();
			// sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			subject.login(token);
		} catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		} catch (LockedAccountException e) {
			return R.error(e.getMessage());
		} catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		// 用户名存入Cookie中或清除
		try {
			if (rememberUser == 1) {
				addCookie(username, request, response);
			} else {
				delCookie(request, response);
			}
		} catch (UnsupportedEncodingException e) {
			return R.error("保存帐号失败");
		}
		return R.ok();
	}

	/**
	 * 管理员退出
	 * 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.GET)
	public String logout() throws Exception {
		if (ShiroUtils.isLogin()) {
			Long userId = ShiroUtils.getUserId();
			if (userId != null
					&& userId.equals(Long.valueOf(Constant.getAdminId()))) { // 系统管理员(退出系统备份数据库)
			}
			ShiroUtils.logout();
		}
		return "redirect:/octopus/sys/login.ftl";

	}

	/**
	 * 保存Cookie
	 * 
	 * @param userName
	 * @param request
	 * @param response
	 */
	public void addCookie(String userName, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(userName)) {
			Cookie nameCookie = new Cookie(Constant.loginSessionAttr,
					URLEncoder.encode(userName, "utf-8"));

			// 设置Cookie的父路径
			nameCookie.setPath(request.getContextPath() + "/");
			// 7天保存时间
			nameCookie.setMaxAge(7 * 24 * 60 * 60);
			response.addCookie(nameCookie);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/sys/getCookie", method = RequestMethod.POST)
	public R getCookie(HttpServletRequest request)
			throws UnsupportedEncodingException {
		R r = new R();
		// 声明一个变量来接收帐号
		String name = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			// 遍历Cookie
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				// 此处类似与Map有name和value两个字段,name相等才赋值,并处理编码问题
				if (Constant.loginSessionAttr.equals(cookie.getName())) {
					name = URLDecoder.decode(cookie.getValue(), "utf-8");
				}

			}
		}
		r.put("userName", name);
		return r;
	}

	/**
	 * 删除Cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 */
	public void delCookie(HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(Constant.loginSessionAttr)) {
					cookie.setValue("");
					cookie.setMaxAge(0);// 立即销毁cookie
					cookie.setPath(request.getContextPath() + "/");// 路径和添加时候的要一样
					response.addCookie(cookie);
					break;
				}
			}
		}
	}
}
