package com.hqc.service;

import java.util.Map;

import com.hqc.entity.MpParkingcChargeEntity;
import java.util.List;
/**
 * 停车记录
 * 
 * @author cxw
 * @date 2017年5月15日
 */
public interface MpParkingcChargeService {
	/**
	 * 查询停车记录
	 * @param map
	 * @return
	 */
	 List<MpParkingcChargeEntity> queryList(Map<String, Object> map);
	 
	 /**
	  * 批量删除停车记录
	  * @param parkingids
	  */
	 void deleteBatch(long[] parkingids);
	 
	 /**
	  * 查询总记录数，分页用
	  * @param map
	  * @return
	  */
	 int queryTotal(Map<String, Object> map);
	 
	 /**
	  * 添加停车消费记录
	  * @param mpParkingcChargeEntity
	  */
	 void  save(MpParkingcChargeEntity mpParkingcChargeEntity);
}
