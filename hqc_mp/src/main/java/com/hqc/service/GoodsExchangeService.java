package com.hqc.service;

import java.util.List;
import java.util.Map;

/**
 * 积分兑换
 * 
 * @author Administrator
 * 
 */
public interface GoodsExchangeService {
	/**
	 * 列表
	 */
	List<Map<String, Object>> queryList(Map<String, Object> map);

	int queryTotal(Map<String, Object> map);

	/**
	 * 删除
	 */
	void deleteBatch(long[] ids);

	/**
	 * 根据会员id查询该会员的所有积分兑换记录
	 * 
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryByMemberId(Map<String, Object> map);

	/**
	 * 查询该会员的所有积分记录的总条数
	 * 
	 * @param id
	 * @return
	 */
	int recordTotal(Map<String, Object> map);
}
