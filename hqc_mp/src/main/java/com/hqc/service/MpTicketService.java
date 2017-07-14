package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpTicketEntity;

/**
 * 景区门票服务接口
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
public interface MpTicketService {

	/**
	 * 保存一个景区门票
	 * 
	 * @param entity
	 *            景区门票
	 * @return 影响行数
	 */
	int save(MpTicketEntity entity);

	/**
	 * 查询景区门票列表
	 * 
	 * @param map
	 * @return 景区门票列表
	 */
	List<MpTicketEntity> queryList(Map<String, Object> map);

	/**
	 * 根据ID查询景区门票
	 * 
	 * @param id
	 *            ID
	 * @return 景区门票
	 */
	MpTicketEntity queryObject(Long id);

	/**
	 * 查询景区门票总数
	 * 
	 * @param map
	 *            筛选条件
	 * @return 景区门票总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 根据ID更新一个景区门票
	 * 
	 * @param entity
	 *            景区门票
	 * @return 影响行数
	 */
	int updateByPrimaryKey(MpTicketEntity entity);

	/**
	 * 根据ID删除景区门票
	 * 
	 * @param id
	 *            ID
	 * @return 影响行数
	 */
	int delete(Long id);
	
	/**
	 * 多行删除
	 * @param ids
	 * @return
	 */
	int deleteBatch(long[] ids);
}
