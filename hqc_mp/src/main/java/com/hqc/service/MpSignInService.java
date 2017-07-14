package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpSignInEntity;

public interface MpSignInService {
	/**
	 * 根据会员id查询当月签到的天数
	 * @param memberId
	 * @return
	 */
	List<MpSignInEntity> queryByMemberId(Map<String, Object> map);
	
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	int save(MpSignInEntity entity);
	
	
	/**
	 * 根据会员id和系统当前时间，判断该会员当天是否已经签到
	 * @param map     Long memberId
	 * 					Long	date
	 * @return  Integer
	 */
	Integer queryHasSign(Map<String, Object> map);
	
	/**
	 * 修改会员信息，因为密码在MpmerberServiceImpl中有加密，但是签到并不需要，所以重写一个
	 * @param mpMember
	 */
	void update(MpMemberEntity mpMember);
}
