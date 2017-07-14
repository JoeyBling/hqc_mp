package com.hqc.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * 微信公众号初始化
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月11日
 * 
 */
@Configuration
public class WXMpConfig {

	@Value("#{wxProperties.appid}")
	private String appid;

	@Value("#{wxProperties.appsecret}")
	private String appsecret;

	@Value("#{wxProperties.token}")
	private String token;

	@Value("#{wxProperties.aeskey}")
	private String aesKey;

	@Value("#{wxProperties.partener_id}")
	private String partenerId;

	@Value("#{wxProperties.partener_key}")
	private String partenerKey;

	/**
	 * 如果出现 org.springframework.beans.BeanInstantiationException
	 * https://github.com/Wechat-Group/weixin-java-tools-springmvc/issues/7
	 * 请添加以下默认无参构造函数
	 */
	protected WXMpConfig() {
	}

	/**
	 * 为了生成自定义菜单使用的构造函数，其他情况Spring框架可以直接注入
	 * 
	 * @param appid
	 * @param appsecret
	 * @param token
	 * @param aesKey
	 */
	protected WXMpConfig(String appid, String appsecret, String token,
			String aesKey) {
		this.appid = appid;
		this.appsecret = appsecret;
		this.token = token;
		this.aesKey = aesKey;
	}

	/**
	 * 微信客户端配置存储
	 * 
	 * @return WxMpConfigStorage
	 */
	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
		configStorage.setAppId(this.appid);
		configStorage.setSecret(this.appsecret);
		configStorage.setToken(this.token);
		configStorage.setAesKey(this.aesKey);
		return configStorage;
	}

	/**
	 * 初始化微信支付配置
	 * 
	 * @return WxPayConfig
	 */
	@Bean
	public WxPayConfig payConfig() {
		WxPayConfig configStorage = new WxPayConfig();
		configStorage.setAppId(this.appid);
		configStorage.setMchId(this.partenerId);
		configStorage.setMchKey(this.partenerKey);
		return configStorage;
	}

	/**
	 * 初始化微信API的Service
	 * 
	 * @return WxMpService
	 */
	@Bean
	public WxMpService wxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}

	/**
	 * 微信支付相关接口
	 * 
	 * @return WxPayService
	 */
	@Bean
	public WxPayService payService() {
		WxPayService payService = new WxPayServiceImpl();
		payService.setConfig(payConfig());
		return payService;
	}

}
