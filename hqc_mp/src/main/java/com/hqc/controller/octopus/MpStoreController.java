package com.hqc.controller.octopus;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpStoreService;
import me.chanjar.weixin.mp.bean.store.WxMpStoreBaseInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpStoreEntity;
import com.hqc.service.MpStoreService;
import com.hqc.task.MpStoreTask;
import com.hqc.util.Constant;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RedisUtil;

/**
 * 门店管理
 * 
 * @author Joey
 * @project:hqc_mp
 * @date：2017年5月20日
 * 
 */
@RestController
@RequestMapping("/octopus/mp/store")
public class MpStoreController extends AbstractController {

	@Resource
	protected WxMpService wxMpService;
	@Resource
	protected MpStoreService mpStoreService;
	@Resource
	protected MpStoreTask mpStoreTask;
	@Resource
	protected RedisUtil redisUtil;

	/**
	 * 所有微信门店列表
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/list")
	@RequiresPermissions("mp:store:list")
	public R list(Integer page, Integer limit, Integer checkpage,
			HttpServletRequest request) throws WxErrorException {
		limit = 10;
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "`poi_id` desc"); // 排序
		List<MpStoreEntity> list = mpStoreService.queryList(map);
		int total = mpStoreService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil)
				.put("remoppo", MpStoreTask.remoppoNumber);
	}

	/**
	 * 所有门店类目信息
	 * 
	 * @throws WxErrorException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/categories")
	@RequiresPermissions("mp:store:list")
	public R categories() throws WxErrorException {
		WxMpStoreService storeService = wxMpService.getStoreService();
		List<String> listCategories = null;
		// Redis缓存
		Object valueWrapper = redisUtil.get(Constant.CATEGORIES_REDIS_NAME);
		listCategories = (List<String>) (valueWrapper == null ? null
				: valueWrapper);
		if (null == listCategories) {
			listCategories = storeService.listCategories(); // 门店类目表
			redisUtil.put(Constant.CATEGORIES_REDIS_NAME, listCategories, null,
					true);
		}
		return R.ok().put("list", listCategories);

	}

	/**
	 * 保存一个门店
	 * 
	 * @param store
	 *            门店基础信息
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("mp:store:save")
	public R save(WxMpStoreBaseInfo store, String location, String cateTypes)
			throws Exception {
		if (StringUtils.isBlank(location)) {
			return R.error("请选择坐标");
		}
		WxMpStoreService storeService = wxMpService.getStoreService();
		if (!store.getProvince().endsWith("省")) {
			store.setProvince(store.getProvince() + "省");
		}
		if (!store.getCity().endsWith("市")) {
			store.setCity(store.getCity() + "市");
		}
		String[] cateType = new String[1];
		cateType[0] = cateTypes;
		store.setCategories(cateType);
		String[] split = location.split(",");
		store.setLatitude(new BigDecimal(split[0])); // 门店所在地理位置的经度
		store.setLongitude(new BigDecimal(split[1])); // 门店所在地理位置的纬度
		store.setSid(""); // 商户自己的id，与门店poi_id对应关系，建议在添加门店时候建立关联关系，具体请参考“微信门店接口
		storeService.add(store);
		return R.ok();
	}

	/**
	 * 根据POIID获取门店基础信息
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/info/{poiId}")
	@RequiresPermissions("mp:store:info")
	public R info(@PathVariable("poiId") String poiId) throws WxErrorException {
		MpStoreEntity entity = mpStoreService.queryObject(poiId);
		return R.ok().put("info", entity);
	}

	/**
	 * 根据POIID删除门店基础信息
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("mp:store:delete")
	@Transactional
	public R delete(String poiId) throws WxErrorException {
		if (StringUtils.isBlank(poiId)) {
			return R.error("非法操作");
		}
		WxMpStoreService storeService = wxMpService.getStoreService();
		String[] split = poiId.split(",");
		for (int i = 0; i < split.length; i++) {
			storeService.delete(split[i]);
		}
		mpStoreService.deleteByPoiId(split);
		return R.ok();
	}

	/**
	 * 根据POIID修改门店基础信息
	 * 
	 * @throws WxErrorException
	 */
	@RequestMapping("/update")
	@RequiresPermissions("mp:store:update")
	@Transactional
	public R update(WxMpStoreBaseInfo store, String location, String cateTypes)
			throws WxErrorException {
		if (StringUtils.isBlank(location)) {
			return R.error("请选择坐标");
		}
		if (StringUtils.isBlank(store.getPoiId())) {
			return R.error("非法操作");
		}
		WxMpStoreService storeService = wxMpService.getStoreService();
		if (!store.getProvince().endsWith("省")) {
			store.setProvince(store.getProvince() + "省");
		}
		if (!store.getCity().endsWith("市")) {
			store.setCity(store.getCity() + "市");
		}
		String[] cateType = new String[1];
		cateType[0] = cateTypes;
		store.setCategories(cateType);
		String[] split = location.split(",");
		store.setLatitude(new BigDecimal(split[0])); // 门店所在地理位置的经度
		store.setLongitude(new BigDecimal(split[1])); // 门店所在地理位置的纬度
		store.setSid(""); // 商户自己的id，与门店poi_id对应关系，建议在添加门店时候建立关联关系，具体请参考“微信门店接口
		storeService.update(store); // 修改门店服务信息
		mpStoreService.delete(store.getPoiId());
		return R.ok();
	}

	/**
	 * 手动同步微信门店数据
	 * 
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping("/syn")
	@RequiresPermissions("mp:store:syn")
	public R syn() throws Exception {
		// 手动同步微信门店数据
		mpStoreTask.autoTask(true);
		return R.ok();
	}

}
