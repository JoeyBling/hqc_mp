package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpArticleDao;
import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.entity.MpArticleEntity;
import com.hqc.service.MpArticleService;

/**
 * 
 * @author jzh
 * @date 2017年5月15日 上午10:42:24
 */
@Service("InformationService")
public class MpArticleServiceImpl implements MpArticleService {

	@Resource
	private MpArticleDao informationDao;

	/**
	 * 查询所有信息资讯消息
	 */
	@Override
	public List<MpArticleEntity> queryList(Map<String, Object> map) {
		return informationDao.queryList(map);
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
		return informationDao.queryTotal();
	}

	@Override
	public int queryById(Integer id) {
		return informationDao.queryById(id);
	}

	@Override
	public int querySys(Integer category_id, Integer id) {
		return informationDao.querySys(category_id, id);
	}

	@Override
	public int save(MpArticleEntity articleEntity) {
		return informationDao.insert(articleEntity);
	}

	@Override
	public MpArticleEntity queryObject(Long articleId) {
		return informationDao.queryObject(articleId);
	}

	/**
	 * 修改
	 */
	public void update(MpArticleEntity entity) {
		informationDao.update(entity);
	}

	/**
	 * 查询图文信息分类
	 */
	public List<MpArticleCategoryEntity> queryArticleCategory() {
		return informationDao.queryArticleCategory();
	}

	/**
	 * 查询该类文章总数
	 */
	public int queryArticleTotalByCategory(Long id) {
		return informationDao.queryArticleTotalByCategory(id);
	}

	@Override
	public void deleteBatch(long[] id) {
		informationDao.deleteBatch(id);
	}

	@Override
	public List<MpArticleEntity> queryAll(String categoryName, int limit,
			int page) {
		return informationDao.queryAll(categoryName, limit, page);
	}

	@Override
	public List<MpArticleEntity> queryByCategoryId(Long categoryId) {
		return informationDao.queryByCategoryId(categoryId);
	}

	@Override
	public List<MpArticleEntity> queryTitle(String categoryName) {
		return informationDao.queryTitle(categoryName);
	}
	@Override
	public MpArticleEntity queryNewById(long categoryId) {
		return informationDao.queryNewById(categoryId);
	}


	@Override
	public int queryTotalByCategoryName(String categoryName) {
		return informationDao.queryTotalByCategoryName(categoryName);
	}

	@Override
	public void updateRead(long articleId) {
		informationDao.updateRead(articleId);
	}

	@Override
	public void updateLike(long articleId) {
		informationDao.updateLike(articleId);
	}


}
