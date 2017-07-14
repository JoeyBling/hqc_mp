package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpSmsCodeDao;
import com.hqc.entity.MpSmsCodeEntity;
import com.hqc.service.MpSmsCodeService;

/**
 * 手机短信验证码服务实现类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
@Service
public class MpSmsCodeServiceImpl implements MpSmsCodeService {

	@Resource
	private MpSmsCodeDao mpSmsCodeDao;

	@Override
	public int save(MpSmsCodeEntity entity) {
		return mpSmsCodeDao.insertSelective(entity);
	}

	@Override
	public List<MpSmsCodeEntity> queryList(Map<String, Object> map) {
		return mpSmsCodeDao.queryList(map);
	}

	@Override
	public MpSmsCodeEntity queryObject(Long id) {
		return mpSmsCodeDao.queryObject(id);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return mpSmsCodeDao.queryTotal(map);
	}

	@Override
	public int updateByPrimaryKey(MpSmsCodeEntity entity) {
		return mpSmsCodeDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int delete(Long id) {
		return mpSmsCodeDao.deleteByPrimaryKey(id);
	}

}
