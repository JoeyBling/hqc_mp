package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 会员等级表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_vip_level")
public class MpVipLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 等级名称
	 */
	@Column
	private String vipName;

	/**
	 * 最小积分
	 */
	@Column
	private Long minIntegral;

	/**
	 * 最大积分
	 */
	@Column
	private Long maxIntegral;

	/**
	 * 描述
	 */
	@Column
	private String about;
	
	/**
	 * 常规日积分
	 */
	@Column
	private Double normalIntegralRule;
	
	/**
	 * 特别日积分
	 */
	@Column
	private Double specialIntegralRule;
	
	/**
	 * 积分系数
	 */
	@Column
	private Double integralCoefficient;
	
	/**
	 * 图标地址
	 */
	@Column
	private String iconUrl;
}
