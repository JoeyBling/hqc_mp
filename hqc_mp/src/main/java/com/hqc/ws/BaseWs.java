package com.hqc.ws;

import java.util.SortedMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.hqc.util.HttpUtil;
import com.hqc.util.WsUtil;
import com.hqc.util.wx.WxMiniUtil;

/**
 * 默认所有WebService必须继承此类实现安全验证
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月6日
 * 
 */
public abstract class BaseWs {

	/**
	 * Slf日志
	 */
	protected final Logger logger = Logger.getLogger(getClass());

	/**
	 * 微信小程序工具类
	 */
	@Resource
	protected WxMiniUtil wxMiniUtil;

	/**
	 * 验证参数是否有空值
	 * 
	 * @param packageParams
	 *            参数集合
	 * @param paramArr
	 *            要验证的参数名数组
	 * @return boolean
	 */
	protected boolean validateParameter(
			SortedMap<Object, Object> packageParams, String... paramArr) {
		return HttpUtil.verificationMap(packageParams, paramArr);
	}

	/**
	 * 验证签名
	 * 
	 * @param characterEncoding
	 *            编码格式
	 * @param packageParams
	 *            请求参数
	 * @param API_KEY
	 *            商户秘钥
	 * @param sign
	 *            请求的sign
	 * @return boolean
	 */
	protected boolean validateSign(String characterEncoding,
			SortedMap<Object, Object> packageParams, String API_KEY, String sign) {
		String nb_sign = WsUtil.createSign("UTF-8", packageParams, API_KEY); // 创建签名
		logger.info("生成的签名:" + nb_sign);
		return nb_sign.equals(sign);
	}

}
