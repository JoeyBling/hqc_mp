package com.hqc.util.wx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 华侨城微信工具类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月11日
 * 
 */
@Component
public class WxUtil {

	/** 登录Session会员Key */
	public final static String MP_MEMBER_LOGIN_SESSION_KEY = "MP_MEMBER_LOGIN_SESSION_KEY";
	/** 登录Session会员OPENID-Key */
	public final static String MP_MEMBER_LOGIN_SESSION_OPENID_KEY = "MP_MEMBER_LOGIN_SESSION_OPENID_KEY";
	/** 登录Session会员UnionId-Key */
	public final static String MP_MEMBER_LOGIN_SESSION_UNIONID_KEY = "MP_MEMBER_LOGIN_SESSION_UNIONID_KEY";
	/** 登录Session会员验证码Key */
	public final static String MP_MEMBER_LOGIN_IMAGE_KAPTCHA_SESSION_KEY = "MP_MEMBER_LOGIN_IMAGE_KAPTCHA_SESSION_KEY";
	/** 注册Session会员验证码Key */
	public final static String MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY = "MP_MEMBER_REG_IMAGE_KAPTCHA_SESSION_KEY";

	/** 找回密码Session会员手机验证码Key */
	public final static String MP_MEMBER_FINDPWD_PHONE_SESSION_KEY = "MP_MEMBER_LOGIN_PHONE_SESSION_KEY";
	/** 注册Session会员手机验证码Key */
	public final static String MP_MEMBER_REG_PHONE_SESSION_KEY = "MP_MEMBER_REG_PHONE_SESSION_KEY";
	/** 注册Session会员手机号码Key(防止恶意注册) */
	public final static String MP_MEMBER_REG_PHONECODE_SESSION_KEY = "MP_MEMBER_REG_PHONECODE_SESSION_KEY";
	/** 登录Session会员手机号码Key(防止恶意登录) */
	public final static String MP_MEMBER_LOGIN_PHONECODE_SESSION_KEY = "MP_MEMBER_LOGIN_PHONECODE_SESSION_KEY";
	/** 找回密码Session会员手机号码Key(防止恶意操作) */
	public final static String MP_MEMBER_REPWD_PHONECODE_SESSION_KEY = "MP_MEMBER_REPWD_PHONECODE_SESSION_KEY";

	/** 更换手机号码，原手机号码验证码Key */
	public final static String MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY = "MP_MEMBER_OLD_PHONE_CODE_SESSION_KEY";
	/** 更换手机号码，原手机号码验证成功标识 */
	public final static String MP_MEMBER_OLD_PHONE_CODE_TRUE = "MP_MEMBER_OLD_PHONE_CODE_TRUE";
	/** 更换手机号码，新手机号码验证码Key */
	public final static String MP_MEMBER_NEW_PHONE_CODE_SESSION_KEY = "MP_MEMBER_NEW_PHONE_CODE_SESSION_KEY";
	/** 更换手机号码，新手机号码Key */
	public final static String MP_MEMBER_NEW_PHONE_SESSION_KEY = "MP_MEMBER_NEW_PHONE_SESSION_KEY";
	
	/** 菜单栏人工服务EventKey */
	public final static String menuKeyCustomerService = "customerService";

	/** 菜单栏每日签到EventKey */
	public final static String menuKeySignIn = "signIn";

	/** REDIS天气查询KEY值 */
	public final static String REDIS_WEATHER_KEY = "REDIS_WEATHER_KEY";

	/** 查询天气的省份(default:广东) */
	public static String WEATHE_PROVINCE;

	/** 查询天气的市区(default:深圳) */
	public static String WEATHE_CITY;

	@Value(value = "${WEATHE.CITY:广东}")
	public void setWEATHE_CITY(String wEATHE_CITY) {
		WEATHE_CITY = wEATHE_CITY;
	}

	@Value(value = "${WEATHE.PROVINCE:深圳}")
	public void setWEATHE_PROVINCE(String wEATHE_PROVINCE) {
		WEATHE_PROVINCE = wEATHE_PROVINCE;
	}

}
