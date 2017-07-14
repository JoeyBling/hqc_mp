package com.hqc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 公众号自动回复表
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月18日
 * 
 */
@Data
@Table(name = "mp_auto_reply")
public class MpAutoReplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 响应类型(1关注公众号 2关键词回复)
	 */
	@Column
	private Integer responseType;

	/**
	 * 回复类型(1文字 2图片 3语音 4视频 5图文)
	 */
	@Column
	private Integer replyType;

	/**
	 * 回复内容
	 */
	@Column
	private String replyText;

	/**
	 * 媒体ID
	 */
	@Column
	private String mediaId;
	
	/**
	 * 关键词(多个使用逗号隔开)
	 */
	@Column
	private String keywords;

	/**
	 * 创建时间
	 */
	@Column
	private Long createTime;

}
