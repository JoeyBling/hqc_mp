package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

/**
 * 微信门店表
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Data
@Table(name = "mp_store")
public class MpStoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 门店ID（适用于微信卡券、微信门店业务），具体定义参考微信门店，与shop_id一一对应
	 */
	@Column
	private String poiId;

	/**
	 * 商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
	 */
	@Column
	private String sid;

	/**
	 * 门店名称
	 */
	@Column
	private String businessName;

	/**
	 * 分店名称
	 */
	@Column
	private String branchName;

	/**
	 * 类目
	 */
	@Column
	private String categories;

	/**
	 * 联系电话
	 */
	@Column
	private String telephone;

	/**
	 * 省份
	 */
	@Column
	private String province;

	/**
	 * 市
	 */
	@Column
	private String city;

	/**
	 * 县/区
	 */
	@Column
	private String district;

	/**
	 * 街道
	 */
	@Column
	private String address;

	/**
	 * 经纬度 ,分隔
	 */
	@Column
	private String location;

}
