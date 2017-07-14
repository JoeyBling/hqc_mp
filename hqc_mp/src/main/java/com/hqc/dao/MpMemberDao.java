package com.hqc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.MpMemberEntity;

public interface MpMemberDao {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public MpMemberEntity queryMpMemberInfoId(long id);

	/**
	 * 
	 * @param phone
	 * @return
	 */
	public List<MpMemberEntity> queryMpMemberInfoPhone(String phone);

	/**
	 * 
	 * @param mpMember
	 */
	public void save(MpMemberEntity mpMember);

	/**
	 * 
	 * @param mpMember
	 */
	public void update(MpMemberEntity mpMember);

	/**
	 * 
	 * @param id
	 */
	public void delete(long id);

	/**
	 * 
	 * @param id
	 */
	public void deleteBatch(long[] id);

	/**
	 * 
	 * @param map
	 * @return
	 */
	public int queryTotal(Map<String, Object> map);

	/**
	 * 
	 * @return
	 */
	public List<MpMemberEntity> queryAllList(Map<String, Object> map);

	/**
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	int updatePassword(Map<String, Object> map);

	/**
	 * 会员登录查询
	 * 
	 * @param phone
	 *            手机号码
	 * @param password
	 *            登录密码
	 * @return MpMemberEntity
	 */
	MpMemberEntity login(@Param("phone") String phone,
			@Param("password") String password);

	/**
	 * 根据会员卡号查询会员信息
	 * 
	 * @param cardNo
	 *            会员卡号
	 * @return 会员信息
	 */
	MpMemberEntity queryByCardNo(@Param("cardNo") String cardNo);

	/**
	 * 根据公众号标识openid查询会员信息
	 * 
	 * @param openId
	 *            公众号标识openid
	 * @return 会员信息
	 */
	MpMemberEntity queryByOpenId(@Param("openId") String openId);

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
	MpMemberEntity queryByUnionId(@Param("unionId") String unionId);
}
