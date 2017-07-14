package com.hqc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.entity.MpArticleEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface MpArticleDao extends BaseDao<MpArticleEntity> {

	/**
	 * 查询用户的所有消息管理
	 * 
	 * @param userId
	 *            用户ID
	 * @return List<String>
	 */
	List<String> queryAllPerms(Long userId);

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
	int querySys(@Param("category_id") Integer category_id,
			@Param("id") Integer id);

	/**
	 * 删除指定的信息资讯详表的信息
	 * 
	 * @param Id
	 */
	void deleteBatch(Integer Id);

	/**
	 * 查询图文信息分类
	 */
	List<MpArticleCategoryEntity> queryArticleCategory();

	/**
	 * 查询该类文章总数
	 */
	int queryArticleTotalByCategory(Long id);



	/**
	 * @param title
	 * @return
	 */
	MpArticleEntity queryentity(String title);


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
	List<MpArticleEntity> queryAll(@Param("categoryName") String categoryName,
			@Param("limit") int limit, @Param("offset") int page);

	/**
	 * 根据图文信息分类ID查询图文信息
	 * 
	 * @param categoryId
	 *            图文信息分类ID
	 * @return 图文信息
	 */
	List<MpArticleEntity> queryByCategoryId(Long categoryId);

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
	 * 阅读量增加
	 * @param articleId
	 */
	void updateRead(long articleId);

	/**
	 * 点赞增加
	 * @param articleId
	 */
	void updateLike(long articleId);

}
