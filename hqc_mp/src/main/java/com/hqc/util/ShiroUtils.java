package com.hqc.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.hqc.entity.SysAdminEntity;


/**
 * Shiro工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月12日 上午9:49:19
 */
public class ShiroUtils {

	/**
	 * 获取Shiro Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取Admin用户
	 * 
	 * @return SysAdminEntity
	 */
	public static SysAdminEntity getAdminEntity() {
		return (SysAdminEntity) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获取Admin用户ID
	 * 
	 * @return Admin用户ID
	 */
	public static Long getUserId() {
		return getAdminEntity().getUserId();
	}

	/**
	 * 设置Shiro属性
	 * 
	 * @param key
	 *            Object
	 * @param value
	 *            Object
	 */
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	/**
	 * 获取Shiro属性
	 * 
	 * @param key
	 *            Object
	 * @return Object
	 */
	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	/**
	 * 获取是否已登录
	 * 
	 * @return boolean
	 */
	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	/**
	 * 登出
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

	/**
	 * 获取验证码
	 * 
	 * @param key
	 *            验证码Key
	 * @return String
	 */
	public static String getKaptcha(String key) {
		String kaptcha = getSessionAttribute(key).toString();
		getSession().removeAttribute(key);
		return kaptcha;
	}

}
