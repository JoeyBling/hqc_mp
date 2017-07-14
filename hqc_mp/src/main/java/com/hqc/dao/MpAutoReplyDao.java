package com.hqc.dao;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.MpAutoReplyEntity;

/**
 * 公众号自动回复
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月18日
 * 
 */
public interface MpAutoReplyDao extends BaseDao<MpAutoReplyEntity> {

	/**
	 * 根据响应类型和关键词查询一条数据
	 * 
	 * @param responseType
	 *            响应类型(1关注公众号 2关键词回复)
	 * @param keywords
	 *            关键词(多个使用逗号隔开)
	 * @return 公众号自动回复表
	 */
	MpAutoReplyEntity queryByKewords(@Param("responseType") Integer responseType,
			@Param("keywords") String keywords);

}
