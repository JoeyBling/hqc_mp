package com.hqc.dao;

import com.hqc.entity.MpWifiEntity;

/**
 * 微信WiFi设备
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpWifiDao extends BaseDao<MpWifiEntity> {

	/**
	 * 根据无线MAC地址bssid删除多个WiFi设备
	 * 
	 * @param bssid
	 *            无线MAC地址
	 * @return 影响行数
	 */
	int deleteByBssid(String[] bssid);

	/**
	 * 删除所有Wifi设备
	 * 
	 * @return 影响行数
	 */
	int deleteAll();

}
