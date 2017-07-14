package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.ScenerySpotDao;
import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.entity.MpScenicSpotEntity;
import com.hqc.service.ScenerySpotService;

/**
 * 景点后台业务实现类
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月15日
 */
@Service
public class ScenerySpotServiceImp implements ScenerySpotService {

	@Resource
	private ScenerySpotDao scenerySpotDao;
	
	@Override
	public List<MpScenerySpotEntity> queryList(Map<String, Object> map) {
		
		return scenerySpotDao.queryList(map);
	}

	@Override
	public int querytoAll(Map<String, Object> map) {
		return scenerySpotDao.queryTotal(map);
	}

	@Override
	public void deletePath(long[] id) {
		scenerySpotDao.deleteBatch(id);
	}

	@Override
	public void update(MpScenerySpotEntity sceneryEntity) {
		 scenerySpotDao.update(sceneryEntity);
	}

	@Override
	public List<MpScenicSpotEntity> queryListscenic() {
		return scenerySpotDao.queryListscenic();
	}

	@Override
	public void save(MpScenerySpotEntity sceneryEntity) {
		scenerySpotDao.save(sceneryEntity);
	}

	@Override
	public MpScenerySpotEntity queryObject(long id) {
		return scenerySpotDao.queryObject(id);
	}

	@Override
	public MpScenicSpotEntity queryscenicById(Long scenicId) {
		
		return scenerySpotDao.queryscenicById(scenicId);
	}

	@Override
	public Integer queryByName(String scenicName) {
		return scenerySpotDao.queryByName(scenicName);
	}

	@Override
	public List<MpScenerySpotEntity> queryObjectList(long[] id) {
		return scenerySpotDao.queryObjectList(id);
	}

	@Override
	public int queryObjectAll(long[] ids) {
		return scenerySpotDao.queryObjectAll(ids);
	}


}
