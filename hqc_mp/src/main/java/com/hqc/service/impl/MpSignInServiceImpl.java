package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpMemberDao;
import com.hqc.dao.MpSignInDao;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpSignInEntity;
import com.hqc.service.MpSignInService;

@Service
public class MpSignInServiceImpl implements MpSignInService {
	@Resource
	private MpSignInDao dao;

	@Resource
	private MpMemberDao mpMemberDao;


	@Override
	public int save(MpSignInEntity entity) {
		return dao.insert(entity);
	}

	@Override
	public Integer queryHasSign(Map<String, Object> map) {
		return dao.queryHasSign(map);
	}

	@Override
	public void update(MpMemberEntity mpMember) {
		mpMemberDao.update(mpMember);
	}

	@Override
	public List<MpSignInEntity> queryByMemberId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.queryByMemberId(map);
	}

}
