package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.ProjectMpDao;
import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.entity.MpProjectEntity;
import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.service.ProjectMpService;

/**
 * 景点项目管理接口实现类
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
@Service("ProjectMpService")
public class ProjectMpServiceIml implements ProjectMpService {

	@Resource
	private ProjectMpDao projectMpDao;
	
	@Override
	public List<MpProjectEntity> queryList(Map<String, Object> map) {
		return projectMpDao.queryList(map);
	}

	@Override
	public int queryToall(Map<String, Object> map) {
		return projectMpDao.queryTotal(map);
	}

	@Override
	public List<MpProjectCategoryEntity> queryListCategory() {
		return projectMpDao.queryListCategory();
	}

	@Override
	public List<MpScenerySpotEntity> queryListScenery() {
		return projectMpDao.queryListScenery();
	}

	@Override
	public void save(MpProjectEntity projectEntity) {
		projectMpDao.save(projectEntity);
	}

	@Override
	public void update(MpProjectEntity projectEntity) {
		projectMpDao.update(projectEntity);
	}

	@Override
	public MpProjectEntity queryObject(long id) {
		return projectMpDao.queryObject(id);
	}

	@Override
	public MpScenerySpotEntity querysceneryById(Long sceneryId) {
		return projectMpDao.querysceneryById(sceneryId);
	}

	@Override
	public MpProjectCategoryEntity querycategoryById(Long categoryId) {
		return projectMpDao.querycategoryById(categoryId);
	}

	@Override
	public List<MpProjectEntity> queryByIds(long[] id) {
		return projectMpDao.queryLists(id);
	}

	@Override
	public void deletePath(long[] id) {
		projectMpDao.deleteBatch(id);
	}

	@Override
	public Integer queryByName(String projectName) {
		return projectMpDao.queryByName(projectName);
	}	
	@Override
	public int queryObjectAll(long[] ids) {
		return projectMpDao.queryObjectAll(ids);
	}

	@Override
	public int queryCategorytAll(long[] categoryIds) {
		return projectMpDao.queryCategorytAll(categoryIds);
	}

	@Override
	public List<MpProjectEntity> queryListBywhere(Long  id) {
		// TODO Auto-generated method stub
		return projectMpDao.queryListBywhere(id);
	}
}
