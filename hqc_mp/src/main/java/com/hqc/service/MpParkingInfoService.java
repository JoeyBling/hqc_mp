package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpParkingcChargeEntity;

/**
 * 停车收费记录
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月5日
 * 
 */
public interface MpParkingInfoService {

	/**
	 * 查询停车收费记录
	 * 
	 * @param map
	 *            Map
	 * @return MpParkingcChargeEntity
	 */
	List<MpParkingcChargeEntity> queryList(Map<String, Object> map);

	/**
	 * 查询停车收费记录总数
	 * 
	 * @param map
	 *            Map
	 * @return 停车收费记录总数
	 */
	int queryTotal(Map<String, Object> map);
}
