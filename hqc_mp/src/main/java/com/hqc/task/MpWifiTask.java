package com.hqc.task;

import javax.annotation.Resource;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;
import com.hqc.entity.MpWifiEntity;
import com.hqc.service.MpWifiService;
import com.hqc.util.wx.MpWiFiHelper;
import com.hqc.util.wx.MpWiFiHelper.Data.baseInfo;

/**
 * 微信WiFi设备自动同步任务
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月22日
 * 
 */
@Component
public class MpWifiTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected MpWifiService mpWifiService;
	@Resource
	protected WxMpService wxMpService;

	/**
	 * 每日凌晨1点自动同步
	 * 
	 * @throws Exception
	 */
	@Transactional
	// @Scheduled(cron = "0 0 1 * * ? ")
	public void autoTask() throws Exception {
		try {
			logger.info("同步微信WiFi设备数据");
			logger.info("删除微信WiFi设备数据条数:" + mpWifiService.deleteAll());
			MpWiFiHelper list = list(0, 20, null); // 调用接口有限制
			System.out.println(list);
			MpWifiEntity entity = null;
			logger.info("总共有Wifi设备:" + list.getData().getTotalCount());
			logger.info("总共Wifi设备页数:"
					+ (int) Math
							.ceil((double) list.getData().getTotalCount() / 20));
			int count = (int) Math
					.ceil((double) list.getData().getTotalCount() / 20);
			for (int i = 0; i < count; i++) {
				if (i != 0) {
					list = list(i, 20, null); // 调用接口有限制
				}
				for (baseInfo wifi : list.getData().getRecords()) {
					entity = new MpWifiEntity();
					entity.setBssid(wifi.getBssid());
					entity.setShopId(wifi.getShopId());
					entity.setProtocolType(Integer.valueOf(wifi
							.getProtocolType()));
					entity.setSsid(wifi.getSsid());
					mpWifiService.save(entity);
				}
			}
		} catch (Exception e) {
			logger.info("微信WiFi设备自动同步任务出错:" + e.getMessage());
			throw e;
		}
	}

	private static final String API_BASE_URL = "https://api.weixin.qq.com/bizwifi/device";

	/**
	 * 获取微信WiFI设备列表
	 * 
	 * @param begin
	 *            分页下标，默认从1开始
	 * @param limit
	 *            每页的个数，默认10个，最大20个
	 * @param shopId
	 *            根据门店id查询
	 * @return MpWiFiHelper
	 * @throws WxErrorException
	 */
	public MpWiFiHelper list(int begin, int limit, String shopId)
			throws WxErrorException {
		String url = API_BASE_URL + "/list";
		JsonObject params = new JsonObject();
		params.addProperty("pageindex", begin);
		params.addProperty("pagesize", limit);
		params.addProperty("shop_id", shopId);
		String response = this.wxMpService.post(url, params.toString());

		WxError wxError = WxError.fromJson(response);
		if (wxError.getErrorCode() != 0) {
			throw new WxErrorException(wxError);
		}
		return MpWiFiHelper.fromJson(response);
	}

	/**
	 * 添加一个WiFi设备
	 * 
	 * @param shop_id
	 *            门店ID
	 * @param ssid
	 *            无线网络设备的ssid
	 * @param password
	 *            无线网络设备的密码
	 * @return WxError
	 * @throws WxErrorException
	 */
	public WxError add(String shop_id, String ssid, String password)
			throws WxErrorException {
		String url = API_BASE_URL + "/add";
		JsonObject params = new JsonObject();
		params.addProperty("shop_id", shop_id);
		params.addProperty("ssid", ssid);
		params.addProperty("password", password);
		String response = this.wxMpService.post(url, params.toString());

		WxError wxError = WxError.fromJson(response);
		if (wxError.getErrorCode() != 0) {
			throw new WxErrorException(wxError);
		}
		return wxError;
	}

}
