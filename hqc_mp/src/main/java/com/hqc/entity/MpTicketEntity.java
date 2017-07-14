package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 景区门票表
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
@Data
@Table(name = "mp_tickets")
public class MpTicketEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 门票名称
	 */
	@Column
	private String ticketName;

	/**
	 * 缩略图
	 */
	@Column
	private String thumbUrl;

	/**
	 * 门票批次编号(扩展字段，暂不启用)
	 */
	@Column
	private String ticketNo;

	/**
	 * 价格
	 */
	@Column
	private Double price;

	/**
	 * 市场价
	 */
	@Column
	private Double marketPrice;

	/**
	 * 是否需要提前购票(0否1是)
	 */
	@Column
	private Boolean advance;
	
	/**
	 * 开售时间
	 */
	@Column
	private Long saleDate;
	
	/**
	 * 结束购票日期
	 */
	@Column
	private Long endBuyDate;

	/**
	 * 开始购票日期
	 */
	@Column
	private Long startBuyDate;
	
	/**
	 * 周末票价是否另算(0否1是)
	 */
	@Column
	private Boolean weekendType;
	
	/**
	 * 周末票价
	 */
	@Column
	private Double weekendPrice;
	
	/**
	 * 折扣
	 */
	@Column
	private Double discount;

	/**
	 * 预定须知
	 */
	@Column
	private String about;

	/**
	 * 门票详情
	 */
	@Column
	private String ticketContent;

	/**
	 * 已售数量
	 */
	@Column
	private Long saleCount;

	/**
	 * 1、上架 2、下架
	 */
	@Column
	private Integer status;

	/**
	 * 最后修改时间
	 */
	@Column
	private Long updateTime;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

}
