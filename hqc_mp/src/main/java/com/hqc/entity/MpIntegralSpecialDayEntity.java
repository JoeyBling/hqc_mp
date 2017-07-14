package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 获得消费积分特别日
 * 
 * @author Administrator
 * 
 */
@Data
@Table(name = "mp_integral_special_day")
public class MpIntegralSpecialDayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 特别日名称
	 */
	@Column
	private String specialName;

	/**
	 * 特别日开始日期
	 */
	@Column
	private Long beginDate;

	/**
	 * 特别日结束日期
	 */
	@Column
	private Long endDate;

	/**
	 * 备注（暂时不用）
	 */
	@Column
	private String memo;
}
