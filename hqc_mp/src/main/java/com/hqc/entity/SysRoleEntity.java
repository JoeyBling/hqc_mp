package com.hqc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 角色
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月10日
 * 
 */
@Table(name = "sys_role")
@Data
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	/**
	 * 角色名称
	 */
	@Column
	private String roleName;

	/**
	 * 备注
	 */
	@Column
	private String remark;

	private List<Long> menuIdList;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

}
