package com.hqc.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信服务类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月11日
 * 
 */
public interface WxService {

	/**
	 * 通过openid获得基本用户信息
	 * 
	 * @param openid
	 *            用户openid
	 * @param lang
	 *            语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
	 * @return 微信用户信息
	 * @throws WxErrorException 
	 */
	WxMpUser getUserInfo(String openid, String lang) throws WxErrorException;

}
