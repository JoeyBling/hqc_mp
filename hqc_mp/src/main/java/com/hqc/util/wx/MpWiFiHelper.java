package com.hqc.util.wx;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 微信WiFi帮助类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月13日
 * 
 */
public class MpWiFiHelper {

	@Override
	public String toString() {
		return ToStringUtils.toSimpleString(this);
	}

	public static MpWiFiHelper fromJson(String json) {
		return WxMpGsonBuilder.create().fromJson(json, MpWiFiHelper.class);
	}

	/**
	 * 错误码，0为正常
	 */
	@SerializedName("errcode")
	private Integer errCode;

	/**
	 * 错误信息
	 */
	@SerializedName("errmsg")
	private String errMsg;

	@SerializedName("data")
	private Data data;

	public static class Data {

		/**
		 * Temp二维码图片url
		 */
		@SerializedName("qrcode_url")
		private String qrcodeUrl;

		@SerializedName("totalcount")
		private Integer totalCount;

		@SerializedName("pageindex")
		private Integer pageIndex;

		@SerializedName("pagecount")
		private Integer pageCount;

		@SerializedName("records")
		private List<baseInfo> records;

		public static class baseInfo {
			@SerializedName("shop_id")
			private String shopId;

			@SerializedName("ssid")
			private String ssid;

			@SerializedName("bssid")
			private String bssid;

			@SerializedName("protocol_type")
			private String protocolType;

			public String getShopId() {
				return shopId;
			}

			public void setShopId(String shopId) {
				this.shopId = shopId;
			}

			public String getSsid() {
				return ssid;
			}

			public void setSsid(String ssid) {
				this.ssid = ssid;
			}

			public String getBssid() {
				return bssid;
			}

			public void setBssid(String bssid) {
				this.bssid = bssid;
			}

			public String getProtocolType() {
				return protocolType;
			}

			public void setProtocolType(String protocolType) {
				this.protocolType = protocolType;
			}

		}

		public Integer getTotalCount() {
			return totalCount;
		}

		public void setTotalCount(Integer totalCount) {
			this.totalCount = totalCount;
		}

		public Integer getPageIndex() {
			return pageIndex;
		}

		public void setPageIndex(Integer pageIndex) {
			this.pageIndex = pageIndex;
		}

		public Integer getPageCount() {
			return pageCount;
		}

		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}

		public List<baseInfo> getRecords() {
			return records;
		}

		public void setRecords(List<baseInfo> records) {
			this.records = records;
		}

		public String getQrcodeUrl() {
			return qrcodeUrl;
		}

		public void setQrcodeUrl(String qrcodeUrl) {
			this.qrcodeUrl = qrcodeUrl;
		}

	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
