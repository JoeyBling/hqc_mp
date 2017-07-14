package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 代金券
 * 
 * @author Administrator
 * 
 */
@Data
@Table(name = "mp_cash_coupon")
public class MpCashCouponEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 代金券名称
	 */
	@Column
	private String cashCouponName;

	/**
	 * 所需积分
	 */
	@Column
	private Integer integral;

	/**
	 * 代金券面值
	 */
	@Column
	private Double faceValue;

	/**
	 * 代金券详细介绍
	 */
	@Column
	private String about;

	/**
	 * 最大允许兑换数量
	 */
	@Column
	private Integer maxExchange;

	/**
	 * 每日允许兑换数量
	 */
	@Column
	private Integer dayExchange;

	/**
	 * 状态 1、上架 2、下架
	 */
	private Integer status;

	/**
	 * 缩略图
	 */
	@Column
	private String cashThumb;

	/**
	 * 库存
	 */
	@Column
	private Integer repertory;

}
