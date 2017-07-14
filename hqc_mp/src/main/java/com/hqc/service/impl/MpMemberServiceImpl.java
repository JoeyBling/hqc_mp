package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import com.hqc.dao.MpMemberDao;
import com.hqc.dao.MpVipLevelDao;
import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpVipLevelEntity;
import com.hqc.service.MpMemberService;
import com.hqc.util.DateUtils;

@Service("MpMemberService")
public class MpMemberServiceImpl implements MpMemberService {

	@Resource
	private MpMemberDao mpMemberDao;
	
	@Resource
	private MpVipLevelDao mpVipLevelDao;

	@Override
	public MpMemberEntity queryMpMemberInfoId(long id) {
		return mpMemberDao.queryMpMemberInfoId(id);
	}

	@Override
	public void save(MpMemberEntity mpMember) {
		mpMember.setPassword(new Sha256Hash(mpMember.getPassword()).toHex());
		mpMember.setCreateTime(DateUtils.getCurrentUnixTime());
		mpMember.setUpdateTime(DateUtils.getCurrentUnixTime());
		mpMemberDao.save(mpMember);
	}

	@Override
	public void update(MpMemberEntity mpMember) {
		if (!StringUtils.isBlank(mpMember.getPassword())) {
			mpMember.setPassword(new Sha256Hash(mpMember.getPassword()).toHex());
		}

		mpMemberDao.update(mpMember);
	}

	@Override
	public void delete(long id) {
		mpMemberDao.delete(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return mpMemberDao.queryTotal(map);
	}

	@Override
	public List<MpMemberEntity> queryAllList(Map<String, Object> map) {
		return mpMemberDao.queryAllList(map);
	}

	@Override
	public List<MpMemberEntity> queryMpMemberInfoPhone(String phone) {
		return mpMemberDao.queryMpMemberInfoPhone(phone);
	}

	@Override
	public void deleteBatch(long[] id) {
		mpMemberDao.deleteBatch(id);
	}

	@Override
	public MpMemberEntity login(String phone, String password) {
		return mpMemberDao.login(phone, password);
	}

	@Override
	public int updatePassword(Map<String, Object> map) {
		return mpMemberDao.updatePassword(map);
	}

	@Override
	public MpMemberEntity queryByCardNo(String cardNo) {
		return mpMemberDao.queryByCardNo(cardNo);
	}

	@Override
	public MpMemberEntity queryByOpenId(String openId) {
		return mpMemberDao.queryByOpenId(openId);
	}

	@Override
	public void updateOpenId(MpMemberEntity member) {
		mpMemberDao.updateOpenId(member);		
	}

	@Override
	public MpMemberEntity updateVipLevel(long id) {
		MpMemberEntity member1 = queryMpMemberInfoId(id);
		MpVipLevelEntity vip = this.mpVipLevelDao.queryLevel(member1.getIntegral());
		MpMemberEntity member = new MpMemberEntity();
		member.setId(id);
		member.setVipLevel(vip.getId().intValue());
		this.mpMemberDao.update(member);
		
	
		return queryMpMemberInfoId(id);
	}
	

	@Override
	public MpMemberEntity queryByUnionId(String unionId) {
		return mpMemberDao.queryByUnionId(unionId);
	}

}
