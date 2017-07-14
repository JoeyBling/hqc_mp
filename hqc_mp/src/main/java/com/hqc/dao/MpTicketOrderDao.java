package com.hqc.dao;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.MpTicketOrderEntity;

/**
 * 购票订单记录
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpTicketOrderDao extends BaseDao<MpTicketOrderEntity> {

	/**
	 * 根据订单编号查询购票订单记录
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param unionid
	 *            公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
	 * @return 购票订单记录
	 */
	MpTicketOrderEntity queryByOrderNo(@Param("orderNo") String orderNo,
			@Param("unionid") String unionid);

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
	int updateByWhere(@Param("orderNo") String orderNo,
			@Param("unionid") String unionid, @Param("status") Integer status);

	/**
	 * 删除一个购票订单记录(只是改变一个字段数值)
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param unionid
	 *            公众号标识unionid(只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段)
	 * @return 影响行数
	 */
	int deleteByWhere(@Param("orderNo") String orderNo,
			@Param("unionid") String unionid);

	/**
	 * 删除一个购票订单记录(只是改变一个字段数值)
	 * 
	 * @param id
	 * @return 影响行数
	 */
	int deleteById(String id);

	int findDelCount(long[] ids);

	/**
	 * 根据订单ID会员ID改变订单删除状态
	 * 
	 * @param id
	 *            订单ID
	 * @param memberId
	 *            会员ID
	 * @param status
	 *            删除状态(0正常1已删除)
	 * @return 影响行数
	 */
	int delByWhere(@Param("orderId") Long id, @Param("memberId") Long memberId,
			@Param("status") Integer status);

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
	int updateStatusByWhere(@Param("orderId") Long id,
			@Param("memberId") Long memberId, @Param("status") Integer status);
}
