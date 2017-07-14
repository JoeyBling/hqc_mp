package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 会员积分收支记录表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_integral_record")
public class MpIntegralRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 关联会员表
	 */
	@Column
	private Long memberId;

	/**
	 * 积分异动数量
	 */
	@Column
	private Long integral;

	/**
	 * 1、进账 2、出账
	 */
	@Column
	private Integer integralType;

	/**
	 * 积分变更说明
	 */
	@Column
	private String about;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

	/**
	 * 会员
	 */
	private MpMemberEntity memberEntity;
}
