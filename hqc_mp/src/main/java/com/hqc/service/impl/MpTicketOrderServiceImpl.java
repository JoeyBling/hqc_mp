package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hqc.dao.MpTicketOrderDao;
import com.hqc.entity.MpTicketOrderEntity;
import com.hqc.service.MpTicketOrderService;
import com.hqc.util.RRException;

/**
 * 购票订单记录服务实现类
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月2日
 * 
 */
@Service
public class MpTicketOrderServiceImpl implements MpTicketOrderService {

	@Resource
	private MpTicketOrderDao mpTicketOrderDao;

	@Override
	public int queryTotal(Map<String, Object> map) {
		return mpTicketOrderDao.queryTotal(map);
	}

	@Override
	public int delete(Long id) {
		return mpTicketOrderDao.deleteByPrimaryKey(id);
	}
	
	
	@Override
	public int delByWhere(Long id,Long memberId,Integer status) {
		return mpTicketOrderDao.delByWhere(id,memberId,status);
	}

	@Override
	public int save(MpTicketOrderEntity entity) {
		return mpTicketOrderDao.insertSelective(entity);
	}

	@Override
	public int updateByPrimaryKey(MpTicketOrderEntity entity) {
		return mpTicketOrderDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public List<MpTicketOrderEntity> queryList(Map<String, Object> map) {
		return mpTicketOrderDao.queryList(map);
	}

	@Override
	public MpTicketOrderEntity queryObject(Long id) {
		return mpTicketOrderDao.queryObject(id);
	}

	@Override
	public MpTicketOrderEntity queryByOrderNo(String orderNo, String unionid) {
		return mpTicketOrderDao.queryByOrderNo(orderNo, unionid);
	}

	@Override
	public int updateByWhere(String orderNo, String unionid, Integer status) {
		return mpTicketOrderDao.updateByWhere(orderNo, unionid, status);
	}

	@Override
	public int deleteByWhere(String orderNo, String unionid) {
		return mpTicketOrderDao.deleteByWhere(orderNo, unionid);
	}

	@Override
	public int deleteById(String id) {
		return mpTicketOrderDao.deleteById(id);
	}

	@Override
	public void deleteBatch(long[] ids) {
		int count = this.mpTicketOrderDao.findDelCount(ids);
		if (count != ids.length) {
			throw new RRException("未支付和正常账单不能删除！");
		}
		this.mpTicketOrderDao.deleteBatch(ids);

	}

	@Override
	public int updateStatusByWhere(Long id, Long memberId, Integer status) {
		return mpTicketOrderDao.updateStatusByWhere(id, memberId, status);
	}

}
