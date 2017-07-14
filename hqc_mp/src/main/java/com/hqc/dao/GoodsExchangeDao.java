package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpGoodsExchangeRecordEntity;

public interface GoodsExchangeDao extends BaseDao<MpGoodsExchangeRecordEntity> {
	List<Map<String, Object>> queryList1(Map<String, Object> map);

	/**
	 * 根据会员id查询该会员的所有积分兑换记录
	 * @param id
	 * @return
	 */
	List<Map<String, Object>>queryByMemberId(Map<String, Object> map);
	/**
	 * 查询该会员的所有积分兑换记录的总条数
	 * @param id
	 * @return
	 */
	int recordTotal(Map<String, Object> map);

}
