package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 角色与菜单对应关系
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月10日
 * 
 */
@Table(name = "sys_role_menu")
@Data
public class SysRoleMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 角色ID
	 */
	@Column
	private Long roleId;

	/**
	 * 菜单ID
	 */
	@Column
	private Long menuId;

	/**
	 * 设置：
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * 
	 * @return Long
	 */
	public Long getId() {
		return id;
	}

}
