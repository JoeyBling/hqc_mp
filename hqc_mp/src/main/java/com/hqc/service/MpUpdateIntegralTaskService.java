package com.hqc.service;

import java.util.List;
import java.util.Map;

/**
 * 积分修改定时方法
 * @author Administrator
 *
 */
public interface MpUpdateIntegralTaskService {
	/**
	 * 修改积分
	 */
	public List<Map<String,Object>> autoUpdateIntegral();

}
