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
 * 会员签到表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_sign_in")
public class MpSignInEntity implements Serializable {
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
	 * 签到时间
	 */
	@Column
	private Long createTime;

	/**
	 * 签到会员
	 */
	@Transient
	private MpMemberEntity mpMemberEntity;
}
