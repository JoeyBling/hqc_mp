package com.hqc.ws;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信门票WS服务
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月6日
 * 
 */
public interface WxTicketWS {

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
	 * @param request
	 *            HttpServletRequest
	 * @return Map<String, Object>
	 */
	public Map<String, Object> decodeUserInfo(String encryptedData, String iv,
			String code, HttpServletRequest request);

	/**
	 * Banner轮播图信息http://192.168.1.88/hqc_mp/ws/banner?page=1&limit=10&sign=2D
	 * AECDE9C8BFCD3DDD941995BAA6953A
	 * 
	 * @param page
	 *            可选页码(二者必须同时存在或为空)
	 * @param limit
	 *            可选条数(二者必须同时存在或为空)
	 * @param request
	 *            HttpServletRequest
	 * @return 轮播图信息
	 */
	public Object banner(Integer page, Integer limit, HttpServletRequest request);

	/**
	 * 门票信息(单个购票信息:http://192.168.1.88/hqc_mp/ws/ticket?ticketId=73&sign=
	 * DBF1CC95C141AED97F9E9B2679BCF604
	 * 分页购票信息:http://192.168.1.88/hqc_mp/ws/ticket
	 * ?page=1&limit=10&sign=2DAECDE9C8BFCD3DDD941995BAA6953A)
	 * 
	 * @param ticketId
	 *            门票ID(为空则查询门票列表信息)
	 * @param page
	 *            可选页码(二者必须同时存在或为空)
	 * @param limit
	 *            可选条数(二者必须同时存在或为空)
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	public Object ticket(Long ticketId, Integer page, Integer limit,
			HttpServletRequest request);

	/**
	 * 生成订单
	 * 
	 * @param encryptedData
	 *            明文,加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @param ticketId
	 *            门票ID
	 * @param orderPhone
	 *            下单手机号码
	 * @param useTime
	 *            使用日期
	 * @param ticketCount
	 *            门票数量
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 * @throws Exception 
	 */
	public Object createOrder(String encryptedData, String iv, String code,
			Long ticketId, String orderPhone, String useTime,
			long ticketCount, HttpServletRequest request)
			throws ParseException, Exception;

	/**
	 * 我的订单(单个订单信息:http://192.168.1.88/hqc_mp/ws/order?unionid=2&orderNo=123&
	 * sign=CA985F39415296F13179D59A1419E5D2
	 * 分页订单信息:http://192.168.1.88/hqc_mp/ws
	 * /order?unionid=2&page=1&limit=10&sign=282571FB5CF752A8D2AB9DD40444D141)
	 * 
	 * @param encryptedData
	 *            明文,加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @param orderNo
	 *            订单编号(为空查询我的订单列表信息)
	 * @param type
	 *            订单状态[1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)]
	 * @param page
	 *            可选页码(二者必须同时存在或为空)
	 * @param limit
	 *            可选条数(二者必须同时存在或为空)
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	public Object order(String encryptedData, String iv, String code,
			String orderNo, Integer type, Integer page, Integer limit,
			HttpServletRequest request);

	/**
	 * 取消订单http://192.168.1.88/hqc_mp/ws/canOrder?unionid=2&orderNo=123&sign=
	 * CA985F39415296F13179D59A1419E5D2
	 * 
	 * @param encryptedData
	 *            明文,加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @param orderNo
	 *            订单编号
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	public Object canOrder(String encryptedData, String iv, String code,
			String orderNo, HttpServletRequest request);

	/**
	 * 删除订单http://192.168.1.88/hqc_mp/ws/delOrder?unionid=2&orderNo=123&sign=
	 * CA985F39415296F13179D59A1419E5D2
	 * 
	 * @param encryptedData
	 *            明文,加密数据
	 * @param iv
	 *            加密算法的初始向量
	 * @param code
	 *            用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取
	 *            session_key api，将 code 换成 openid 和 session_key
	 * @param orderNo
	 *            订单编号
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	public Object delOrder(String encryptedData, String iv, String code,
			String orderNo, HttpServletRequest request);
}
