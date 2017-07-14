package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.ScenicSpotDao;
import com.hqc.entity.MpScenicSpotEntity;
import com.hqc.service.ScenicSpotService;

/**
 * 景区后台业务实现类
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月16日
 */
@Service
public class ScenicSpotServiceImp implements ScenicSpotService {

	@Resource
	private ScenicSpotDao scenicSpotDao;
	
	@Override
	public List<MpScenicSpotEntity> queryList(Map<String, Object> map) {
		return scenicSpotDao.queryList(map);
	}

	@Override
	public int queryToall(Map<String, Object> map) {
		return scenicSpotDao.queryTotal(map);
	}

	@Override
	public void deletePath(long[] id) {
		scenicSpotDao.deleteBatch(id);
	}

	@Override
	public void save(MpScenicSpotEntity scenic) {
		scenicSpotDao.save(scenic);
	}

	@Override
	public void update(MpScenicSpotEntity scenic) {
		scenicSpotDao.update(scenic);
	}

	@Override
	public MpScenicSpotEntity queryObject(long id) {
		return scenicSpotDao.queryObject(id);
	}

	@Override
	public boolean queryScenicName(String scenicName) {
		
		if(scenicName.equals((scenicSpotDao.queryEntity(scenicName)).getScenicName())){		
			return true;
		}else{			
			return false;
		}
	}

	@Override
	public Integer queryByName(String scenicName) {
		return scenicSpotDao.queryByName(scenicName);
	}

}
