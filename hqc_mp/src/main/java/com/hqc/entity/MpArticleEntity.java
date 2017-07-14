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
 * 图文信息表
 * 
 * @author cxw
 * @date 2017年5月13日
 */
@Data
@Table(name = "mp_article")
public class MpArticleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 标题
	 */
	@Column
	private String title;

	/**
	 * 缩略图
	 */
	@Column
	private String thumb;

	/**
	 * 关联图文信息分类表
	 */
	@Column
	private Long categoryId;

	/**
	 * 图文信息内容
	 */
	@Column
	private String content;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

	/**
	 * 阅读量
	 */
	@Column
	private Long readCount;

	/**
	 * 点赞数量
	 */
	@Column
	private Long likeCount;

	/**
	 * 来源
	 */
	@Column
	private String comeFrom;

	/**
	 * 作者
	 */
	@Column
	private String author;

	/**
	 * 关联系统用户表
	 */
	@Column
	private Long userId;

	/**
	 * 0、未审核 1、正常 2、审核不通过
	 */
	@Column
	private Integer status;

	/**
	 * 图文信息类别
	 */
	@Transient
	private MpArticleCategoryEntity mpArticleCategoryEntity;
	
}
