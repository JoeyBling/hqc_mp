package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpTicketOrderEntity;

/**
 * 购票订单记录服务接口
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
public interface MpTicketOrderService {

	/**
	 * 保存一个购票订单记录
	 * 
	 * @param entity
	 *            购票订单记录
	 * @return 影响行数
	 */
	int save(MpTicketOrderEntity entity);

	/**
	 * 查询购票订单记录列表
	 * 
	 * @param map
	 * @return 购票订单记录列表
	 */
	List<MpTicketOrderEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询购票订单记录
	 * 
	 * @param id
	 *            ID
	 * @return 购票订单记录
	 */
	MpTicketOrderEntity queryObject(Long id);

	/**
	 * 根据订单编号查询购票订单记录
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param unionid
	 *            公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
	 * @return 购票订单记录
	 */
	MpTicketOrderEntity queryByOrderNo(String orderNo, String unionid);

	/**
	 * 查询购票订单记录总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 购票订单记录
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个购票订单记录
	 * 
	 * @param entity
	 *            购票订单记录
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpTicketOrderEntity entity);

	/**
	 * 更新一个购票订单记录
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param unionid
	 *            公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
	 * @param status
	 *            订单状态：1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
	 * @return 影响行数
	 */
	int updateByWhere(String orderNo, String unionid, Integer status);

	/**
	 * 根据ID删除购票订单记录
	 * 
	 * @param id
	 *            ID
	 * @return 影响行数
	 */
	int delete(Long id);

	/**
	 * 删除一个购票订单记录(只是改变一个字段数值)
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param unionid
	 *            公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
	 * @return 影响行数
	 */
	int deleteByWhere(String orderNo, String unionid);

	/**
	 * 删除一个购票订单记录(只是改变一个字段数值)
	 * 
	 * @param id
	 * @return 影响行数
	 */
	int deleteById(String id);

	void deleteBatch(long[] ids);

	/**
	 * 据订单ID会员ID改变订单删除状态
	 * 
	 * @param id
	 *            订单ID
	 * @param memberId
	 *            会员ID
	 * @param status
	 *            删除状态(0正常1已删除)
	 * @return 影响行数
	 */
	int delByWhere(Long id, Long memberId, Integer status);

	/**
	 * 据订单ID会员ID改变订单状态
	 * 
	 * @param id
	 *            订单ID
	 * @param memberId
	 *            会员ID
	 * @param status
	 *            订单状态：1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
	 * @return 影响行数
	 */
	int updateStatusByWhere(Long id, Long memberId, Integer status);
}
