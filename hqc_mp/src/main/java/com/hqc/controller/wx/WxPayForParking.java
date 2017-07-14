package com.hqc.controller.wx;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hqc.entity.MpParkingcChargeEntity;
import com.hqc.payforparking.ws.ParkingInfo;
import com.hqc.payforparking.ws.WebServiceForPay;
import com.hqc.payforparking.ws.WebServiceForPaySoap;
import com.hqc.service.MpParkingInfoService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;
import com.hqc.util.wx.MD5Util;

/**
 * 停车收费
 * 
 * @author cxw
 * @date 2017年6月1日
 */
@RestController
@RequestMapping("/wx/car")
public class WxPayForParking extends WxAuthController {

	/** 停车付费第三方接口 */
	private WebServiceForPay pay = new WebServiceForPay();

	@Resource
	private MpParkingInfoService infoService;

	/**
	 * 查询停车账单
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return ModelAndView
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toPay")
	public ModelAndView getParkingPaymentInfo(HttpServletRequest request)
			throws UnsupportedEncodingException {
		ModelAndView view = new ModelAndView();
		if (!isLogin(request)) {
			view.setViewName("/wx/404.ftl");
			return view;
		}
		// 获取页面值
		String platehead = request.getParameter("platehead");
		String platefoot = request.getParameter("platefoot");
		// 非空判断
		if (StringUtils.isBlank(platefoot)) {
			view.setViewName("/wx/car/wxCarPay.ftl");
			view.addObject("resMsg", "请输入车牌号");
			return view;
		}
		String plate = (platehead == null ? "" : platehead) + platefoot;
		// 停车场ID具体怎么获得待定
		Integer parkId = 1;
		// 第三方用户ID获取方法待定
		Integer appId = 1;

		String appkey = "b20887292a374637b4a9d6e9f940b1e6";
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
		String date = sdf.format(new Date());
		
		//通过接口获取车场ID，但是没测试数据，没测试过！
		//Integer parkId = this.queryParkId(plate, appkey, appId, date);
		// 组装key
		String key = MD5Util.MD5(parkId + plate + date + appkey);

		// 获得工具类
		WebServiceForPaySoap webServiceForPaySoap = pay.getWebServiceForPaySoap();
		// 获取信息
		String pay = webServiceForPaySoap.getParkingPaymentInfo(appId, key,
				parkId, plate);
		JSONObject js = JSONObject.parseObject(pay);
		// 判断结果并取值
		Integer resCode = js.getInteger("resCode");
		if (resCode == 1) {
			view.setViewName("/wx/car/wxCarPay.ftl");
			view.addObject("resMsg", js.getString("resMsg"));
			return view;
		}

		JSONArray obj = js.getJSONArray("data");
		List<ParkingInfo> parkingInfos = JSONArray.parseArray(
				obj.toJSONString(), ParkingInfo.class);

		ParkingInfo parkingInfo = parkingInfos.get(0);
		System.out.println(pay);
		Integer hour = parkingInfo.getElapsedTime() / 60;

		view.addObject("hour", hour);
		view.addObject("plate", plate);
		view.addObject("parkingInfo", parkingInfo);
		view.setViewName("/wx/car/wxCarPayFor.ftl");

		return view;
	}

	/**
	 * 停车收费记录
	 * 
	 * @param page
	 *            页码
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	@RequestMapping("/chargelist")
	@ResponseBody
	public R getParkingInfo(Integer page, HttpServletRequest request) {
		if (!isLogin(request)) {
			return R.error(100, "用户已过期，请重新登陆");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int limit = 10;
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "`update_time` desc");
		map.put("memberId", getMember(request).getId());
		List<MpParkingcChargeEntity> list = infoService.queryList(map);
		int total = infoService.queryTotal(map);
		PageUtils pageutil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageutil);
	}
	
	/**
	 * 查询停车的车场ID，没有测试数据，暂时做不了！
	 * @param plate
	 * @param appkey
	 * @param appId
	 * @param date
	 * @return
	 */
	public Integer queryParkId(String plate,String appkey,Integer appId,String date){
		WebServiceForPaySoap webServiceForPaySoap = this.pay.getWebServiceForPaySoap();
		
		String key = MD5Util.MD5(plate+date+appkey);
		
		String parking = webServiceForPaySoap.getParkingLotByCar(appId, key, plate); 
		JSONObject js = JSONObject.parseObject(parking);
		Integer resCode = js.getInteger("resCode");
		if (resCode == 1) {
			throw new RRException( js.getString("resMsg"));
		}
		JSONArray  obj = js.getJSONArray("data");
		//获取集合的第一个元素（只有一个）中的车场ID
		int parkId = ( (JSONObject)obj.get(0)).getIntValue("parkId");
		return parkId;
	}

}
