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
 * 景点项目表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_project")
public class MpProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 项目名称
	 */
	@Column
	private String projectName;

	/**
	 * 项目简介
	 */
	@Column
	private String about;

	/**
	 * 所属景点id（关联表mp_scenery_spot字段id）
	 */
	@Column
	private Long sceneryId;

	/**
	 * 项目类型id（关联表mp_project_category字段id）
	 */
	@Column
	private Long categoryId;

	/**
	 * 项目缩略图地址
	 */
	@Column
	private String thumbUrl;

	/**
	 * 纬度
	 */
	@Column
	private String lat;

	/**
	 * 经度
	 */
	@Column
	private String lng;

	/**
	 * 项目详细介绍
	 */
	@Column
	private String projectContent;

	/**
	 * 项目状态：1、启用 0、停用
	 */
	@Column
	private Integer status;

	/**
	 * 所属景点
	 */
	@Transient
	private MpScenerySpotEntity mpScenerySpotEntity;

	/**
	 * 项目类型
	 */
	@Transient
	private MpProjectCategoryEntity mpProjectCategoryEntity;
}
