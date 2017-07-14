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
 * 停车消费记录
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_parking_charge")
public class MpParkingcChargeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 停车记录Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 车牌号
	 */
	@Column
	private String plate;

	/**
	 * 订单号
	 */
	@Column
	private String orderNo;

	/**
	 * 车场回馈的消费单号
	 */
	@Column
	private String billNo;

	/**
	 * 会员id，关联表（mp_member）字段id
	 */
	@Column
	private Long memberId;

	/**
	 * 停车消费金额
	 */
	@Column
	private Double totalFee;

	/**
	 * 车场驻留开始时间
	 */
	@Column
	private Long startTime;

	/**
	 * 车场驻留结束时间
	 */
	@Column
	private Long endTime;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

	/**
	 * 最后修改时间
	 */
	@Column
	private Long updateTime;

	/**
	 * 1=正常 2=未支付成功 3=未支付 6=退单
	 */
	@Column
	private Integer status;

	/**
	 * 用户名
	 */
	@Transient
	private String trueName;

}
