package com.hqc.util.wx;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 微信WiFi门店帮助类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月13日
 * 
 */
public class MpWiFiShopHelper {

	@Override
	public String toString() {
		return ToStringUtils.toSimpleString(this);
	}

	public static MpWiFiShopHelper fromJson(String json) {
		return WxMpGsonBuilder.create().fromJson(json, MpWiFiShopHelper.class);
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
		@SerializedName("totalcount")
		private Integer totalCount;

		@SerializedName("pageindex")
		private Integer pageIndex;

		@SerializedName("pagecount")
		private Integer pageCount;

		@SerializedName("records")
		private List<baseInfo> records;

		public static class baseInfo {
			// 门店ID（适用于微信连Wi-Fi业务）
			@SerializedName("shop_id")
			private String shopId;

			// 门店名称
			@SerializedName("shop_name")
			private String shopName;

			// 无线网络设备的ssid，未添加设备为空，多个ssid时显示第一个
			@SerializedName("ssid")
			private String ssid;

			// 无线网络设备的ssid列表，返回数组格式
			@SerializedName("ssid_list")
			private String[] ssidList;

			// 门店内设备的设备类型，0-未添加设备，1-专业型设备，4-密码型设备，5-portal自助型设备，31-portal改造型设备
			@SerializedName("protocol_type")
			private Integer protocolType;

			// 商户自己的id，与门店poi_id对应关系，建议在添加门店时候建立关联关系，具体请参考“微信门店接口”
			@SerializedName("sid")
			private String sid;

			// 门店ID（适用于微信卡券、微信门店业务），具体定义参考微信门店，与shop_id一一对应
			@SerializedName("poi_id")
			private String poiId;

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

			public String getShopName() {
				return shopName;
			}

			public void setShopName(String shopName) {
				this.shopName = shopName;
			}

			public String[] getSsidList() {
				return ssidList;
			}

			public void setSsidList(String[] ssidList) {
				this.ssidList = ssidList;
			}

			public Integer getProtocolType() {
				return protocolType;
			}

			public void setProtocolType(Integer protocolType) {
				this.protocolType = protocolType;
			}

			public String getSid() {
				return sid;
			}

			public void setSid(String sid) {
				this.sid = sid;
			}

			public String getPoiId() {
				return poiId;
			}

			public void setPoiId(String poiId) {
				this.poiId = poiId;
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
