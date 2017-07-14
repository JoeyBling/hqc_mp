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
 * 商品信息
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_goods")
public class MpGoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 商品名称
	 */
	@Column
	private String goodsName;

	/**
	 * 商品缩略图
	 */
	@Column
	private String goodsThumb;

	/**
	 * 商品编号，识别商品的唯一编号，暂时不用
	 */
	@Column
	private String goodsNo;

	/**
	 * 商品分类，关联商品分类表
	 */
	@Column
	private Integer categoryId;

	/**
	 * 最多可以订多少天之后的票，0不限制
	 */
	@Column
	private Long daysLimit;

	/**
	 * 门票有效日期到哪天，0代表不限制
	 */
	@Column
	private Long endTime;

	/**
	 * 当前库存
	 */
	@Column
	private Long repertory;

	/**
	 * 市场价
	 */
	@Column
	private Double marketPrice;

	/**
	 * 商品价格
	 */
	@Column
	private Double price;

	/**
	 * 所需积分
	 */
	@Column
	private Long integral;

	/**
	 * 商品介绍
	 */
	@Column
	private String about;

	/**
	 * 预定须知
	 */
	@Column
	private String notice;

	/**
	 * 最大允许兑换数量，0代表不限制
	 */
	@Column
	private Long maxExchange;

	/**
	 * 每日最大兑换数量，0代表不限制
	 */
	@Column
	private Long dayExchange;

	/**
	 * 1、上架 2、下架
	 */
	@Column
	private Integer status;

	/**
	 * 关联系统用户表(数据操作用户)
	 */
	@Column
	private Long userId;

	/**
	 * 商品类别
	 */
	@Transient
	private MpGoodsCategoryEntity mpGoodsCategoryEntity;

	/**
	 * 系统用户
	 */
	@Transient
	private SysAdminEntity sysAdminEntity;
}
