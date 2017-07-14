package com.hqc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.dao.MpParkingcChargeDao;
import com.hqc.entity.MpParkingcChargeEntity;
import com.hqc.service.MpParkingcChargeService;
import com.hqc.util.RRException;
/**
 * 停车记录
 * 
 * @author cxw
 * @date 2017年5月15日
 */
@Service("mpParkingcChargeService")
public class MpParkingcChargeServiceImpl implements MpParkingcChargeService{
	
	@Resource
	private MpParkingcChargeDao mpParkingcChargeDao;
	
	@Override
	public List<MpParkingcChargeEntity> queryList(Map<String, Object> map) {
		return this.mpParkingcChargeDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return this.mpParkingcChargeDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void deleteBatch(long[] parkingids) {
		
		int count =this.mpParkingcChargeDao.findDelCount(parkingids);
		
		//如果要删除的数量和传入的id数量不同，则删除失败
		if (count != parkingids.length) {
			 throw new RRException("未支付和正常账单不能删除！");
		}	
		this.mpParkingcChargeDao.deleteBatch(parkingids);
		
	}

	@Override
	public void save(MpParkingcChargeEntity mpParkingcChargeEntity) {
		if (mpParkingcChargeEntity.getTotalFee() == null) {
			mpParkingcChargeEntity.setTotalFee(0.00);
		}
		this.mpParkingcChargeDao.save(mpParkingcChargeEntity);
		
	}
}
