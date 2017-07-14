package com.hqc.controller.octopus;

import org.apache.log4j.Logger;

import com.hqc.entity.SysAdminEntity;
import com.hqc.util.ShiroUtils;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
abstract class AbstractController {
	protected final Logger logger = Logger.getLogger(getClass());

	protected SysAdminEntity getAdmin() {
		return ShiroUtils.getAdminEntity();
	}

	protected Long getAdminId() {
		return ShiroUtils.getUserId();
	}
}
