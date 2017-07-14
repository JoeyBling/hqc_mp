package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * 购票订单记录表
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
@Data
@Table(name = "mp_ticket_order")
public class MpTicketOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 订单编号
	 */
	@Column
	private String orderNo;

	/**
	 * 订单标题
	 */
	@Column
	private String orderTitle;

	/**
	 * 门票id
	 */
	@Column
	private Long ticketId;

	/**
	 * 门票数量
	 */
	@Column
	private Long ticketCount;

	/**
	 * 有效期开始时间
	 */
	@Column
	private Long startTime;

	/**
	 * 有效期结束时间
	 */
	@Column
	private Long endTime;

	/**
	 * 下单手机号码
	 */
	@Column
	private String orderPhone;

	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	@Column
	private String unionid;

	/**
	 * 会员id（关联表mp_member字段id）
	 */
	@Column
	private Long memberId;

	/**
	 * 订单总金额
	 */
	@Column
	private Double totalFee;

	/**
	 * 订单状态：1、已支付 2、支付失败 3、待支付 4、已关闭(用户取消订单或超时未支付)
	 */
	@Column
	private Integer status;

	/**
	 * 删除状态(0正常1已删除)
	 */
	@Column
	private Boolean del;

	/**
	 * 最后修改时间
	 */
	@Column
	private Long updateTime;

	/**
	 * 下单时间
	 */
	@Column
	private Long createTime;

	// 景区门票表
	@Transient
	private MpTicketEntity mpTicketEntity;
	// 会员表
	@Transient
	private MpMemberEntity mpMemberEntity;

}
