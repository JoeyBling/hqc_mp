package com.hqc.service;

import java.util.List;
import java.util.Map;

import com.hqc.entity.MpIntegralRecordEntity;

/**
 * 微信端我的积分
 * @author Administrator
 *
 */
public interface WxMemberIntegerRecordService {
	 /**
	    * 查询数据
	    * @param map
	    * @return
	    */
		public List<MpIntegralRecordEntity> queryList(Map<String, Object> map);
		public int queryTotal(Map<String, Object> map) ;
}
