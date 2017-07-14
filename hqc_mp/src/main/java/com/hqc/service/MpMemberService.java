package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpMemberEntity;

public interface MpMemberService {

	/**
	 * 根据id做查询
	 * 
	 * @param id
	 * @return
	 */
	MpMemberEntity queryMpMemberInfoId(long id);

	/**
	 * 修改会员等级
	 * 
	 * @param id
	 * @return
	 */
	MpMemberEntity updateVipLevel(long id);

	/**
	 * 根据手机号做查询
	 * 
	 * @param phone
	 * @return
	 */
	List<MpMemberEntity> queryMpMemberInfoPhone(String phone);

	/**
	 * 添加
	 * 
	 * @param mpMember
	 */
	void save(MpMemberEntity mpMember);

	/**
	 * 修改
	 * 
	 * @param mpMember
	 */
	void update(MpMemberEntity mpMember);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(long id);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void deleteBatch(long[] id);

	/**
	 * 查询条数
	 * 
	 * @param map
	 * @return
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<MpMemberEntity> queryAllList(Map<String, Object> map);

	/**
	 * 会员登录查询
	 * 
	 * @param phone
	 *            手机号码
	 * @param password
	 *            登录密码
	 * @return MpMemberEntity
	 */
	MpMemberEntity login(String phone, String password);

	/**
	 * 修改密码
	 * 
	 * @param map
	 * @return
	 */
	int updatePassword(Map<String, Object> map);

	/**
	 * 根据会员卡号查询会员信息
	 * 
	 * @param cardNo
	 *            会员卡号
	 * @return 会员信息
	 */
	MpMemberEntity queryByCardNo(String cardNo);

	/**
	 * 根据公众号标识openid查询会员信息
	 * 
	 * @param openId
	 *            公众号标识openid
	 * @return 会员信息
	 */
	MpMemberEntity queryByOpenId(String openId);

	/**
	 * 更新会员OPENID
	 * 
	 * @param member
	 *            MpMemberEntity
	 */
	void updateOpenId(MpMemberEntity member);

	/**
	 * 根据公众号标识unionId查询会员信息
	 * 
	 * @param openId
	 *            公众号标识unionId
	 * @return 会员信息
	 */
	MpMemberEntity queryByUnionId(String unionId);
}
