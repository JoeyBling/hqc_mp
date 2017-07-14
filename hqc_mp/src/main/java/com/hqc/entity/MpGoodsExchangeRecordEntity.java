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
 * 积分商品兑换记录
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_goods_exchange_record")
public class MpGoodsExchangeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 订单号
	 */
	@Column
	private String orderNo;

	/**
	 * 商品id（关联表mp_goods字段id）
	 */
	@Column
	private Long goodsId;

	/**
	 * 会员id（关联表mp_member字段id）
	 */
	@Column
	private Long memberId;

	/**
	 * 兑换所用积分
	 */
	@Column
	private Long integral;

	/**
	 * 商品编号（兑换的商品编号，多个编号逗号隔开）【暂时不使用此字段】
	 */
	@Column
	private String goodsNos;

	/**
	 * 兑换数量
	 */
	@Column
	private Long exchangeCount;

	/**
	 * 兑换码(兑换成功之后生成的兑换码，用户使用的时候提供的票据）
	 */
	@Column
	private String exchangeCode;

	/**
	 * 兑换人手机号码
	 */
	@Column
	private String personPhone;

	/**
	 * 使用日期(用于兑换门票的时候，指定的使用日期）
	 */
	@Column
	private Long useTime;

	/**
	 * 使用状态（1、已使用 0、未使用）【如果兑换的是门票，则存在使用状态】
	 */
	@Column
	private Integer useStatus;

	/**
	 * 兑换时间
	 */
	@Column
	private Long createTime;

	/**
	 * 标记为普通商品或代金卷 1代金卷，2普通商品（门票）
	 */
	@Column
	private Integer goodsType;

	/**
	 * 商品
	 */
	@Transient
	private MpGoodsEntity mpGoodsEntity;

	/**
	 * 用户
	 */
	@Transient
	private MpMemberEntity mpMemberEntity;

	@Transient
	private MpCashCouponEntity mpCashCouponEntity;
}
