package com.hqc.dao;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:31:36
 */
public interface BaseDao<T> extends Mapper<T> {

	/**
	 * 保存一个实体
	 * 
	 * @param t
	 */
	void save(T t);

	void save(Map<String, Object> map);

	void saveBatch(List<T> list);

	/**
	 * 更新一个实体
	 * 
	 * @param t
	 * @return
	 */
	int update(T t);

	int update(Map<String, Object> map);

	/**
	 * 根据ID删除一个实体
	 */
	int delete(Object id);

	int delete(Map<String, Object> map);

	/**
	 * 删除多个实体
	 * 
	 * @param id
	 *            long[]
	 * @return
	 */
	int deleteBatch(long[] id);

	/**
	 * 根据ID查询一个实体
	 * 
	 * @param id
	 * @return
	 */
	T queryObject(Object id);

	/**
	 * 查询实体集合
	 * 
	 * @param map
	 *            Map
	 * @return List<T>
	 */
	List<T> queryList(Map<String, Object> map);

	List<T> queryList(Object id);

	/**
	 * 查询总数
	 * 
	 * @param map
	 *            Map
	 * @return int
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 查询总数
	 * 
	 * @return int
	 */
	int queryTotal();
}
