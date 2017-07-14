package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.dao.MpAutoReplyDao;
import com.hqc.entity.MpAutoReplyEntity;
import com.hqc.service.MpAutoReplyService;
import com.hqc.util.DateUtils;

/**
 * 公众号自动回复服务接口实现类
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月18日
 * 
 */
@Transactional
@Service
public class MpAutoReplyServiceImpl implements MpAutoReplyService {

	@Resource
	protected MpAutoReplyDao dao;

	@Override
	@Transactional
	public int save(MpAutoReplyEntity mpAutoReply) throws Exception {
		mpAutoReply.setCreateTime(DateUtils.getCurrentUnixTime());
		int insert = dao.insert(mpAutoReply);
		return insert;
	}

	@Override
	public List<MpAutoReplyEntity> queryList(Map<String, Object> map) {
		return dao.queryList(map);
	}

	@Override
	public MpAutoReplyEntity queryObject(Long id) {
		return dao.queryObject(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal(map);
	}

	@Override
	public int updateByPrimaryKey(MpAutoReplyEntity mpAutoReply) {
		return dao.updateByPrimaryKey(mpAutoReply);
	}

	@Override
	public MpAutoReplyEntity queryByKewords(Integer responseType,
			String keywords) {
		return dao.queryByKewords(responseType, keywords);
	}

	@Override
	public void deleteBatch(long[] replyIds) {
		dao.deleteBatch(replyIds);
	}

}
