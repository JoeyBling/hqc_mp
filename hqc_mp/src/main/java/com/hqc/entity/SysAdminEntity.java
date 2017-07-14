package com.hqc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * 系统用户
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月10日
 * 
 */
@Data
@Table(name = "sys_user")
public class SysAdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	/**
	 * 用户名
	 */
	@Column
	private String username;

	/**
	 * 密码
	 */
	@Column
	private transient String password;

	/**
	 * 邮箱
	 */
	@Column
	private String email;

	/**
	 * 手机号
	 */
	@Column
	private String mobile;

	/**
	 * 状态 0：禁用 1：正常
	 */
	@Column
	private Integer status;

	/**
	 * 角色ID列表
	 */
	@Transient
	private List<Long> roleIdList;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

}
