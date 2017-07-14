package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;

/**
 * 微信WiFi设备表
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
@Data
@Table(name = "mp_wifi")
public class MpWifiEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 门店ID
	 */
	@Column
	private String shopId;

	/**
	 * 连网设备ssid
	 */
	@Column
	private String ssid;

	/**
	 * 无线MAC地址
	 */
	@Column
	private String bssid;

	/**
	 * 门店内设备的设备类型，0-未添加设备，4-密码型设备，31-portal型设备
	 */
	@Column
	private Integer protocolType;

}
