package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.hqc.entity.MpWifiEntity;
import com.hqc.service.MpWifiService;
import com.hqc.task.MpWifiTask;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.wx.MpWiFiHelper;
import com.hqc.util.wx.MpWiFiShopHelper;

/**
 * 微信WiFi设备管理
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月20日
 * 
 */
@RestController
@RequestMapping("/octopus/mp/wifi")
public class MpWifiController extends AbstractController {

	@Resource
	protected WxMpService wxMpService;
	@Resource
	protected MpWifiService mpWifiService;
	@Resource
	protected MpWifiTask mpWifiTask;

	/**
	 * 所有微信WiFi设备列表
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/list")
	@RequiresPermissions("mp:wifi:list")
	public R list(Integer page, Integer limit, Integer checkpage,
			HttpServletRequest request) throws WxErrorException {
		limit = 10;
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "`shop_id` desc"); // 排序
		List<MpWifiEntity> list = mpWifiService.queryList(map);
		int total = mpWifiService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * WiFi门店列表
	 * 
	 * @return Map
	 * @throws WxErrorException
	 */
	@RequestMapping("/wifiShop")
	@RequiresPermissions("mp:wifi:list")
	public R wifiShop() throws WxErrorException {
		String url = "https://api.weixin.qq.com/bizwifi/shop/list";
		JsonObject params = new JsonObject();
		params.addProperty("pageindex", 1);
		params.addProperty("pagesize", 20);
		String response = this.wxMpService.post(url, params.toString());
		WxError wxError = WxError.fromJson(response);
		if (wxError.getErrorCode() != 0) {
			throw new WxErrorException(wxError);
		}
		return R.ok().put("wifiShop", MpWiFiShopHelper.fromJson(response));
	}

	/**
	 * 保存一个微信WiFi设备
	 * 
	 * @param entity
	 *            微信WiFi设备
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("mp:wifi:save")
	@Transactional
	public R save(MpWifiEntity entity, String password) throws Exception {
		if (StringUtils.isBlank(entity.getShopId())) {
			return R.error("请选择门店");
		}
		if (entity.getProtocolType() == null) {
			return R.error("请选择设备类型");
		}
		if (StringUtils.isBlank(password)) {
			return R.error("请输入Wifi密码");
		}
		if (password.length() < 8 || password.length() > 24) {
			return R.error("Wifi密码长度必须在8到24位之间");
		}
		if (entity.getProtocolType() == 4) { // 添加密码型设备
			mpWifiTask.add(entity.getShopId(), entity.getSsid(), password);
		}
		entity.setBssid("");
		mpWifiService.save(entity);
		// mpWifiTask.autoTask(); /因为要扫码激活后才能同步.不能直接下载联网二维码进行激活
		return R.ok("添加成功，请下载二维码联网激活成功后再进行同步数据!");
	}

	/**
	 * 根据无线MAC地址删除微信WiFi设备
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("mp:wifi:delete")
	@Transactional
	public R delete(String[] bssid) throws WxErrorException {
		String url = "https://api.weixin.qq.com/bizwifi/device/delete";
		for (int i = 0; i < bssid.length; i++) {	//删除设备
			JsonObject params = new JsonObject();
			params.addProperty("bssid", bssid[i]);
			String response = this.wxMpService.post(url, params.toString());
			WxError wxError = WxError.fromJson(response);
			if (wxError.getErrorCode() != 0) {
				throw new WxErrorException(wxError);
			}
		}
		mpWifiService.deleteByBssid(bssid);
		return R.ok();
	}

	/**
	 * 获取WiFi连接物料二维码
	 * 
	 * @param shopid
	 *            门店ID
	 * @param ssid
	 *            已添加到门店下的无线网络名称
	 * @param type
	 *            物料样式编号： 0-纯二维码，可用于自由设计宣传材料； 1-二维码物料，155mm×215mm(宽×高)，可直接张贴
	 * 
	 * @return Map
	 * @throws WxErrorException
	 */
	@RequestMapping("/download")
	@RequiresPermissions("mp:wifi:list")
	public R download(String shopid, String ssid, Integer type)
			throws WxErrorException {
		String url = "https://api.weixin.qq.com/bizwifi/qrcode/get";
		JsonObject params = new JsonObject();
		params.addProperty("shop_id", shopid);
		params.addProperty("ssid", ssid);
		params.addProperty("img_id", type);
		String response = this.wxMpService.post(url, params.toString());
		WxError wxError = WxError.fromJson(response);
		if (wxError.getErrorCode() != 0) {
			throw new WxErrorException(wxError);
		}
		return R.ok().put("qrcode", MpWiFiHelper.fromJson(response));
	}

	/**
	 * 手动同步微信WiFi设备
	 * 
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping("/syn")
	@RequiresPermissions("mp:wifi:syn")
	public R syn() throws Exception {
		// 手动同步微信WiFi设备数据
		mpWifiTask.autoTask();
		return R.ok();
	}

}
