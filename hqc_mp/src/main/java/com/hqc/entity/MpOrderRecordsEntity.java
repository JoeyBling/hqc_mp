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
 * 商品订单记录表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_order_records")
public class MpOrderRecordsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 订单类型(1购票订单 2积分商城门票订单 3积分商城代金卷订单)
	 */
	@Column
	private Integer type;

	/**
	 * 关联门票id(对应购票订单)
	 */
	@Column
	private Long ticketId;

	/**
	 * 关联代金券ID(对应积分商城代金卷订单)
	 */
	@Column
	private Long couponId;

	/**
	 * 关联商品表ID
	 */
	@Column
	private Long goodsId;

	/**
	 * 取票凭证码（通过短信告诉用户）
	 */
	@Column
	private String itemCode;

	/**
	 * 兑换数量（通过短信告诉用户）
	 */
	@Column
	private Long itemCount;

	/**
	 * 关联会员表
	 */
	@Column
	private Long memberId;

	/**
	 * 接收短信的手机号码
	 */
	@Column
	private String phone;

	/**
	 * 订单号
	 */
	@Column
	private String orderNo;

	/**
	 * 订单创建时间
	 */
	@Column
	private Long createTime;

	/**
	 * 订单更新时间
	 */
	@Column
	private Long updateTime;

	/**
	 * 订单消费金额
	 */
	@Column
	private Double price;

	/**
	 * 订单消耗积分
	 */
	@Column
	private Double integral;

	/**
	 * 0、已下单 1、已兑换 2、已取票
	 */
	@Column
	private Integer status;

	/**
	 * 商品
	 */
	@Transient
	private MpGoodsEntity goodsEntity;

	/**
	 * 关联代金券(对应积分商城代金卷订单)
	 */
	@Transient
	private MpCashCouponEntity mpCashCouponEntity;

	/**
	 * 关联门票(对应购票订单)
	 */
	@Transient
	private MpTicketEntity mpTicketEntity;

	/**
	 * 用户
	 */
	@Transient
	private MpMemberEntity mpMemberEntity;

	/**
	 * 订单类型
	 * 
	 * @author Joey
	 * @email:2434387555@qq.com
	 * @date：2017年6月6日
	 * 
	 */
	enum OrderType {
		/**
		 * 购票订单
		 */
		ticket(1),
		/**
		 * 积分商城门票订单
		 */
		shopTicket(2),
		/**
		 * 积分商城代金卷订单
		 */
		shopCoupon(2);
		private int value;

		private OrderType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 订单状态
	 * 
	 * @author Joey
	 * @email:2434387555@qq.com
	 * @date：2017年6月6日
	 * 
	 */
	enum Status {
		/**
		 * 已下单
		 */
		ordered(0),
		/**
		 * 已兑换
		 */
		exchange(1),
		/**
		 * 已取票
		 */
		getTicket(2);
		private int value;

		private Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
