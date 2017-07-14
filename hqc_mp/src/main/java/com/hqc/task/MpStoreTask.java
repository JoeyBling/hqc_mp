package com.hqc.task;

import java.util.List;

import javax.annotation.Resource;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpStoreService;
import me.chanjar.weixin.mp.bean.store.WxMpStoreInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hqc.entity.MpStoreEntity;
import com.hqc.service.MpStoreService;

/**
 * 微信门店自动同步任务
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月22日
 * 
 */
@Component
public class MpStoreTask {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected WxMpService wxMpService;

	@Resource
	protected MpStoreService mpStoreService;

	/** 同步数据剩余机会 */
	public static Integer remoppoNumber = 5;

	/**
	 * 每日凌晨1点自动同步
	 * 
	 * @param remoppo
	 *            是否是手动同步数据
	 * @throws Exception
	 */
	@Transactional
	// @Scheduled(cron = "0 0 1 * * ? ")
	public void autoTask(boolean remoppo) throws Exception {
		try {
			if (remoppo) {
				remoppoNumber--;
				logger.info("手动同步微信数据，今日剩余机会" + remoppoNumber);
			} else {
				remoppoNumber = 5;
			}
			logger.info("删除微信门店数据条数:" + mpStoreService.deleteAll());
			WxMpStoreService storeService = wxMpService.getStoreService();
			List<WxMpStoreInfo> listStore = storeService.listAll(); // 调用接口有限制
			if (null != listStore) {
				MpStoreEntity entity = null;
				for (WxMpStoreInfo wxMpStoreInfo : listStore) {
					entity = new MpStoreEntity();
					entity.setSid(wxMpStoreInfo.getBaseInfo().getSid());
					entity.setAddress(wxMpStoreInfo.getBaseInfo().getAddress());
					entity.setBranchName(wxMpStoreInfo.getBaseInfo()
							.getBranchName());
					entity.setBusinessName(wxMpStoreInfo.getBaseInfo()
							.getBusinessName());
					entity.setCategories(ArrayUtils.toString(wxMpStoreInfo
							.getBaseInfo().getCategories()));
					entity.setCity(wxMpStoreInfo.getBaseInfo().getCity());
					entity.setDistrict(wxMpStoreInfo.getBaseInfo()
							.getDistrict());
					entity.setLocation(wxMpStoreInfo.getBaseInfo()
							.getLatitude()
							+ ","
							+ wxMpStoreInfo.getBaseInfo().getLongitude());
					entity.setPoiId(wxMpStoreInfo.getBaseInfo().getPoiId());
					entity.setProvince(wxMpStoreInfo.getBaseInfo()
							.getProvince());
					entity.setTelephone(wxMpStoreInfo.getBaseInfo()
							.getTelephone());
					mpStoreService.save(entity);
				}
			}
		} catch (Exception e) {
			logger.info("微信门店自动同步任务出错:" + e.getMessage());
			throw e;
		}
	}
}
