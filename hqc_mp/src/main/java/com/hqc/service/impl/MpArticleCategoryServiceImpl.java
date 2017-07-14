package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.dao.MpArticleCategoryDao;
import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.service.MpArticleCategoryService;
import com.hqc.util.RRException;

@Transactional
@Service("InformationKindService")
public class MpArticleCategoryServiceImpl implements MpArticleCategoryService {

	@Resource
	private MpArticleCategoryDao dao;

	@Override
	public List<MpArticleCategoryEntity> queryList(Map<String, Object> map) {

		return dao.queryList(map);
	}

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return dao.queryTotal();
	}

	@Override
	public MpArticleCategoryEntity queryObject(Long articleId) {
		return dao.queryObject(articleId);
	}

	@Override
	public int save(MpArticleCategoryEntity articleCategoryEntity) {
		return dao.insert(articleCategoryEntity);
	}

	@Override
	public List<MpArticleCategoryEntity> querybyparent() {
		return dao.querybyparent();
	}

	@Override
	@Transactional
	public int deleteBatch(String info) {
		long[] infos = new long[info.split(",").length];
		int sum=0;
		int num=0;
		for (int i = 0; i < info.split(",").length; i++) {
			infos[i] = Long.valueOf(info.split(",")[i]);
			num=queryExist(infos[i]);
			if(num==0)
			{
				sum=dao.queryChild(infos[i]);//判断父类类别下是否存在文章，存在文章不可删除
			}else
			{
				return 2;
			}
		}
		if(sum==0)//等于0则父类下面不存在有子类文章则可以删除
		{
			int deleteBatch = dao.deleteBatch(infos);
			if (deleteBatch != infos.length) {
				throw new RRException("其中包含系统内置项,不可删除");
			}
		}else{
			return 0;
		}
		return 1;
		
	}

	@Override
	public void update(MpArticleCategoryEntity articleCategoryEntity) {
		dao.update(articleCategoryEntity);
	}

	@Override
	public int querybyland(Long id) {
		return dao.querybyland(id);
	}
	/**
	 * 父类根据ID查询子类列表中是否存在文章，存在父类不可删除
	 * @param id 
	 * @return
	 */
	@Override
	public int queryChild(Long id) {
		// TODO Auto-generated method stub
		return dao.queryChild(id);
	}
	/**
	 * <!-- ID查询PARENTID看是否存在子类菜单 -->
	 * @param parentId
	 * @return
	 */
	@Override
	public int queryExist(Long parentId) {
		return dao.queryExist(parentId);
	}

	@Override
	public int queryByName(String name) {
		return dao.queryByName(name);
	}

	@Override
	public MpArticleCategoryEntity queryByCategoryName(String name) {
		return dao.queryByCategoryName(name);
	}
}
