package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpSignInEntity;

public interface MpSignInDao extends BaseDao<MpSignInEntity> {
	/**
	 * 根据会员id查询当月签到的天数
	 * 
	 * @param memberId
	 * @return
	 */
	List<MpSignInEntity> queryByMemberId(Map<String, Object> map);

	/**
	 * 根据会员id和系统当前时间，判断该会员当天是否已经签到
	 * 
	 * @param map
	 *            Long memberId Long date
	 * @return Integer
	 */
	Integer queryHasSign(Map<String, Object> map);

}
