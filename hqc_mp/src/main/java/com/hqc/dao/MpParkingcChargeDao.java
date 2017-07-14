package com.hqc.dao;

import com.hqc.entity.MpParkingcChargeEntity;

/**
 * 停车消费记录数据交互接口
 * 
 * @author cxw
 * @date 2017年5月15日
 */
public interface MpParkingcChargeDao extends BaseDao<MpParkingcChargeEntity>{
	/**
	 * 查询可删除的数量，确认是否与输入一致；
	 * @param ids
	 * @return
	 */
	int findDelCount(long [] ids);
}
