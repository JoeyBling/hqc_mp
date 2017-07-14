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
 * 会员表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_member")
public class MpMemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 手机号码
	 */
	@Column
	private String phone;

	/**
	 * 会员卡号
	 */
	@Column
	private String cardNo;

	/**
	 * 会员姓名
	 */
	@Column
	private String trueName;

	/**
	 * 微信昵称
	 */
	@Column
	private String nickName;

	/**
	 * 公众号标识openid
	 */
	@Column
	private String openId;

	/**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	 */
	@Column
	private String unionid;

	/**
	 * 会员头像
	 */
	@Column
	private String avatar;

	/**
	 * 密码
	 */
	@Column
	private String password;

	/**
	 * 上一年度积分
	 */
	@Column
	private Long lastYearIntegral;

	/**
	 * 本年度积分
	 */
	@Column
	private Long currentYearIntegral;

	/**
	 * 用户当前总积分
	 */
	@Column
	private Long integral;

	/**
	 * 会员级别，关联会员级别表ID
	 */
	@Column
	private Integer vipLevel;

	/**
	 * 会员注册时间
	 */
	@Column
	private Long createTime;

	/**
	 * 信息更新时间
	 */
	@Column
	private Long updateTime;

	/**
	 * 1、启用 2、禁用
	 */
	@Column
	private Integer status;

	/**
	 * 会员生日
	 */
	@Column
	private Long birthday;

	/**
	 * 会员级别
	 */
	@Transient
	private MpVipLevelEntity vipLevelEntity;

}
