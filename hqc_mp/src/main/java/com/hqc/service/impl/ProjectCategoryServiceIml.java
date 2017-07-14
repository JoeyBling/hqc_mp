package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.ProjectCategoryDao;
import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.service.ProjectCategoryService;

/**
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
@Service
public class ProjectCategoryServiceIml implements ProjectCategoryService {

	@Resource
	private ProjectCategoryDao categoryDao;
	@Override
	public List<MpProjectCategoryEntity> queryList(Map<String, Object> map) {
		return categoryDao.queryList(map);
	}

	@Override
	public int queryToall(Map<String, Object> map) {
		return categoryDao.queryTotal(map);
	}

	@Override
	public int deletePath(long[] id) {
		return categoryDao.deleteBatch(id);
	}

	@Override
	public List<MpProjectCategoryEntity> queryByIds(long[] id) {
		return categoryDao.queryListByIds(id);
	}

	@Override
	public void save(MpProjectCategoryEntity category) {
		categoryDao.save(category);
	}

	@Override
	public MpProjectCategoryEntity queryObject(long id) {
		return categoryDao.queryObject(id);
	}

	@Override
	public void update(MpProjectCategoryEntity categoryEntity) {
		categoryDao.update(categoryEntity);
	}

	@Override
	public List<MpProjectCategoryEntity> queryListproCategory() {
		return categoryDao.queryListproCategory();
	}

	@Override
	public Integer queryByName(String categoryName) {
		return categoryDao.queryByName(categoryName);
	}
	
	
}
