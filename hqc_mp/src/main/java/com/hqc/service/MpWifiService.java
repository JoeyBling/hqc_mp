package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpWifiEntity;

/**
 * 微信WiFi设备服务接口
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpWifiService {

	/**
	 * 保存一个微信WiFi设备
	 * 
	 * @param entity
	 *            微信WiFi设备
	 * @return 影响行数
	 */
	int save(MpWifiEntity entity);

	/**
	 * 查询微信WiFi设备列表
	 * 
	 * @param map
	 * @return 微信WiFi设备
	 */
	List<MpWifiEntity> queryList(Map<String, Object> map);

	/**
	 * 查询微信WiFi设备总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 微信WiFi设备总数
	 */
	int queryTotal(Map<String, Object> map);

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
