package com.hqc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 菜单管理
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月10日
 * 
 */
@Table(name = "sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 */
	@Column
	private Long parentId;

	/**
	 * 父菜单名称
	 */
	@Transient
	private String parentName;

	/**
	 * 菜单名称
	 */
	@Column
	private String name;

	/**
	 * 菜单URL
	 */
	@Column
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	@Column
	private String perms;

	/**
	 * 类型 0：目录 1：菜单 2：按钮
	 */
	@Column
	private Integer type;

	/**
	 * 菜单图标
	 */
	@Column
	private String icon;

	/**
	 * 排序
	 */
	@Column
	private Integer orderNum;

	/**
	 * ztree属性
	 */
	private Boolean open;

	private List<?> list;

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getMenuId() {
		return menuId;
	}

	/**
	 * 设置：父菜单ID，一级菜单为0
	 * 
	 * @param parentId
	 *            父菜单ID，一级菜单为0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：父菜单ID，一级菜单为0
	 * 
	 * @return Long
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：菜单名称
	 * 
	 * @param name
	 *            菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：菜单名称
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：菜单URL
	 * 
	 * @param url
	 *            菜单URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：菜单URL
	 * 
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 * 
	 * @return 授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public String getPerms() {
		return perms;
	}

	/**
	 * 设置授权(多个用逗号分隔，如：user:list,user:create)
	 * 
	 * @param perms
	 *            授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public void setPerms(String perms) {
		this.perms = perms;
	}

	/**
	 * 获取类型 0：目录 1：菜单 2：按钮
	 * 
	 * @return 类型 0：目录 1：菜单 2：按钮
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置类型 0：目录 1：菜单 2：按钮
	 * 
	 * @param type
	 *            类型 0：目录 1：菜单 2：按钮
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 设置：菜单图标
	 * 
	 * @param icon
	 *            菜单图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取：菜单图标
	 * 
	 * @return String
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置：排序
	 * 
	 * @param orderNum
	 *            排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：排序
	 * 
	 * @return Integer
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "SysMenuEntity [menuId=" + menuId + ", parentId=" + parentId
				+ ", parentName=" + parentName + ", name=" + name + ", url="
				+ url + ", perms=" + perms + ", type=" + type + ", icon="
				+ icon + ", orderNum=" + orderNum + ", open=" + open
				+ ", list=" + list + "]";
	}
}
