package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 横幅图片
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月6日
 * 
 */
@Data
@Table(name = "mp_banner")
public class MpBannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 横幅标题
	 */
	@Column
	private String title;

	/**
	 * 横幅图片缩略图
	 */
	@Column
	private String thumbUrl;

	/**
	 * 横幅图片链接地址
	 */
	@Column
	private String url;

	/**
	 * 状态(1启用 2禁用)
	 */
	@Column
	private Integer status;

}
