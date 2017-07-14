package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpAutoReplyEntity;

/**
 * 公众号自动回复服务接口
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月17日
 * 
 */
public interface MpAutoReplyService {

	/**
	 * 新增一个公众号自动回复
	 * 
	 * @param mpAutoReply
	 *            公众号自动回复
	 * @return 影响行数
	 */
	int save(MpAutoReplyEntity mpAutoReply) throws Exception;

	/**
	 * 查询公众号自动回复列表
	 * 
	 * @param map
	 *            筛选条件
	 * @return 公众号自动回复列表
	 */
	List<MpAutoReplyEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询公众号自动回复
	 * 
	 * @param id
	 *            公众号自动回复ID
	 * @return 公众号自动回复
	 */
	MpAutoReplyEntity queryObject(Long id);

	/**
	 * 根据响应类型和关键词查询一条数据
	 * 
	 * @param responseType
	 *            响应类型(1关注公众号 2关键词回复)
	 * @param keywords
	 *            关键词(多个使用逗号隔开)
	 * @return 公众号自动回复表
	 */
	MpAutoReplyEntity queryByKewords(Integer responseType, String keywords);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个公众号自动回复
	 * 
	 * @param mpAutoReply
	 *            公众号自动回复
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpAutoReplyEntity mpAutoReply);

	/**
	 * 删除自动回复
	 * 
	 * @param replyIds
	 *            long[]
	 */
	void deleteBatch(long[] replyIds);

}
