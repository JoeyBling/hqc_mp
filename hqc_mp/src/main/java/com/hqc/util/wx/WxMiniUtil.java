package com.hqc.util.wx;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * 微信小程序工具类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月8日
 * 
 */
@Component
public class WxMiniUtil {

	/**
	 * Slf日志
	 */
	protected final Logger logger = Logger.getLogger(getClass());

	/**
	 * 小程序唯一标识 (在微信小程序管理后台获取)
	 */
	@Value("#{wxProperties.miniAppid}")
	public String appid;

	/**
	 * 小程序的 app secret (在微信小程序管理后台获取)
	 */
	@Value("#{wxProperties.miniAppsecret}")
	public String appsecret;

	/**
	 * 授权（必填）
	 */
	@Value("#{wxProperties.miniGrantType}")
	public String grantType;

	/**
	 * 解密用户敏感数据
	 * 
	 * @param encryptedData
	 *            明文,加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @return Map<String, Object>
	 */
	public Map<String, Object> decodeUserInfo(String encryptedData, String iv,
			String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 登录凭证不能为空
		if (code == null || code.length() == 0) {
			map.put("code", 500);
			map.put("msg", "code 不能为空");
			return map;
		}

		// 小程序唯一标识 (在微信小程序管理后台获取)
		String wxspAppid = appid;
		// 小程序的 app secret (在微信小程序管理后台获取)
		String wxspSecret = appsecret;
		// 授权（必填）
		String grant_type = grantType;

		// ////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
		// ////////////////
		// 请求参数
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret
				+ "&js_code=" + code + "&grant_type=" + grant_type;
		// 发送请求
		String sr = HttpRequest.sendGet(
				"https://api.weixin.qq.com/sns/jscode2session", params);
		// 解析相应内容（转换成json对象）
		JSONObject json = JSONObject.parseObject(sr);
		if (json.get("session_key") == null) {
			map.put("code", 500);
			map.put("msg", "获取用户信息出错");
			return map;
		}
		// 获取会话密钥（session_key）
		String session_key = json.get("session_key").toString();
		// 用户的唯一标识（openid）
		String openid = (String) json.get("openid");
		logger.info("用户的唯一标识（openid）" + openid);

		// ////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
		try {
			String result = AesCbcUtil.decrypt(encryptedData, session_key, iv,
					"UTF-8");
			if (null != result && result.length() > 0) {
				map.put("code", 0);
				map.put("msg", "解密成功");
				logger.info("微信小程序之获取并解密用户数据解密成功");
				JSONObject userInfoJSON = JSONObject.parseObject(result);
				Map<String, Object> userInfo = new HashMap<String, Object>();
				userInfo.put("openId", userInfoJSON.get("openId"));
				userInfo.put("nickName", userInfoJSON.get("nickName"));
				userInfo.put("gender", userInfoJSON.get("gender"));
				userInfo.put("city", userInfoJSON.get("city"));
				userInfo.put("province", userInfoJSON.get("province"));
				userInfo.put("country", userInfoJSON.get("country"));
				userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
				userInfo.put("unionId", userInfoJSON.get("unionId"));
				map.put("userInfo", userInfo);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("code", 500);
		map.put("msg", "解密失败");
		return map;
	}

}
