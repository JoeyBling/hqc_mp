package com.hqc.controller.wx;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import me.chanjar.weixin.common.bean.result.WxError;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.hqc.service.MpWifiService;
import com.hqc.task.MpWifiTask;
import com.hqc.util.Constant;
import com.hqc.util.wx.MpWiFiHelper;

/**
 * 微信WiFi设备管理
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月20日
 * 
 */
@Controller
@RequestMapping("/wx/wifi")
public class WxWifiController extends BaseController {

	@Resource
	protected WxMpService wxMpService;
	@Resource
	protected MpWifiService mpWifiService;
	@Resource
	protected MpWifiTask mpWifiTask;

	/**
	 * 连接WiFi
	 * 
	 * @throws WxErrorException
	 * @throws IOException 
	 */
	@RequestMapping("/link")
	public void link(Integer type, Model model, HttpServletResponse httpResponse)
			throws WxErrorException, IOException {
		type = type == null ? 1 : type; // 默认是物料二维码
		MpWiFiHelper list = mpWifiTask.list(0, 1, null);
		if (list.getData().getRecords().size() < 1) {
			httpResponse.sendRedirect(Constant.getNetAddress() + "wx/404.html");
		}
		String url = "https://api.weixin.qq.com/bizwifi/qrcode/get";
		JsonObject params = new JsonObject();
		params.addProperty("shop_id", list.getData().getRecords().get(0)
				.getShopId());
		params.addProperty("ssid", list.getData().getRecords().get(0).getSsid());
		params.addProperty("img_id", type);
		String response = this.wxMpService.post(url, params.toString());
		WxError wxError = WxError.fromJson(response);
		if (wxError.getErrorCode() != 0) {
			throw new WxErrorException(wxError);
		}
		model.addAttribute("qrcode", MpWiFiHelper.fromJson(response).getData()
				.getQrcodeUrl());
		httpResponse.sendRedirect(MpWiFiHelper.fromJson(response).getData()
				.getQrcodeUrl());
	}

}
