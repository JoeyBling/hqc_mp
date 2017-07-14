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
 * 图文信息分类表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_article_category")
public class MpArticleCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 分类名称
	 */
	@Column
	private String categoryName;

	/**
	 * 父ID
	 */
	@Column
	private Long parentId;

	/**
	 * 1=栏目页 2=单页 （如果是单页，只能添加一篇文章）
	 */
	@Column
	private Long categoryType;

	/**
	 * 是否系统内置分类（1、是 0、不是），系统内置分类不允许删除
	 */
	@Column
	private Long isSystem;

	/**
	 * 父类名称
	 */
	@Transient
	private String parentName;

}
