package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 手机短信验证码
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
@Data
@Table(name = "mp_sms_code")
public class MpSmsCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	private Long id;

	/**
	 * 手机号码
	 */
	@Column
	private String tel;

	/**
	 * 验证码
	 */
	@Column
	private String code;

	/**
	 * 过期时间
	 */
	@Column
	private Long expireTime;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

}
