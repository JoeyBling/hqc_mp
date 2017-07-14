package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.entity.MpArticleEntity;

/**
 * 
 * @author jzh
 * @date 2017年5月15日 上午10:36:24
 */
public interface MpArticleService {

	/**
	 * 查询所有信息资讯
	 * 
	 * @param userId
	 * @return list<String>
	 */
	List<MpArticleEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 删除资讯信息
	 * 
	 * @param id
	 *            从表ID值
	 * 
	 */
	void deleteBatch(long[] id);

	/**
	 * 根据主表ID查询从表category_id(关联图文信息分类表)
	 * 
	 * @param id
	 *            资讯ID
	 * @return int
	 */
	int queryById(Integer id);

	/**
	 * 根据主表category_id查询主表图文信息分类 中是否属于内置系统对象
	 * 
	 * @param id
	 *            category_id(关联图文信息分类表)
	 * @return int
	 */
	int querySys(Integer category_id, Integer id);

	/**
	 * 保存一个图文信息
	 * 
	 * @param articleEntity
	 *            图文信息表
	 * @return 影响的行数
	 */
	int save(MpArticleEntity articleEntity);

	/**
	 * 根据图文信息ID查询
	 * 
	 * @param articleId
	 *            图文信息ID
	 * @return 图文信息
	 */
	MpArticleEntity queryObject(Long articleId);

	/**
	 * 根据图文信息分类ID查询图文信息
	 * 
	 * @param categoryId
	 *            图文信息分类ID
	 * @return 图文信息
	 */
	List<MpArticleEntity> queryByCategoryId(Long categoryId);

	/**
	 * 修改
	 */
	void update(MpArticleEntity entity);

	/**
	 * 查询图文信息分类
	 */
	List<MpArticleCategoryEntity> queryArticleCategory();

	/**
	 * 查询该类文章总数
	 */
	int queryArticleTotalByCategory(Long id);
	/**
	 * 根据标题做查询
	 * @param title
	 * @return
	 */
	/**
	 * 查询所有动态或酒店餐饮列表
	 * 
	 * @param categoryName
	 *            (酒店餐饮/最新动态)
	 * @param limit
	 *            每页显示的条数
	 * @param page
	 *            当前页数
	 * @return MpArticleEntity
	 */
	List<MpArticleEntity> queryAll(String categoryName, int limit, int page);

	 /**
     * 根据类别名称做查询
     * @param categoryName
     * @return
     */
	List<MpArticleEntity> queryTitle(String categoryName);
	/**
	 * 根据资讯分类ID查询信息
	 * 
	 * @param categoryId
	 * @return
	 */
	MpArticleEntity queryNewById(long categoryId);

	/**
	 * 查询所有动态或酒店餐饮总数
	 * 
	 * @param categoryName
	 *            (酒店餐饮/最新动态)
	 * @return int
	 */
	int queryTotalByCategoryName(String categoryName);
	/**
	 * 阅读增加
	 * @param articleId
	 */
	void updateRead(long articleId);

	/**
	 * 点赞增加
	 * @param articleId
	 */
	void updateLike(long articleId);

}
