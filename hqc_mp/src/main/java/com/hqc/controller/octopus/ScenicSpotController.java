package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpScenicSpotEntity;
import com.hqc.service.ScenerySpotService;
import com.hqc.service.ScenicSpotService;
import com.hqc.util.DateUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 景区后台管理控制器
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月16日
 */
@RestController
@RequestMapping("/octopus/scenicSpot/scenic")
public class ScenicSpotController {

	@Resource
	private ScenicSpotService scenicSpotService;

	@Resource
	private ScenerySpotService scenerySpotService;
	
	/**
	 * 所有景区
	 * 
	 * @param page
	 * @param limit
	 * @param checkpage
	 * @param orderby
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("scenic:spot:list")
	public R list(Integer page, Integer limit, Integer checkpage,
			String orderby, String scenicName) {
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
		map.put("orderby", "update_time desc");// 根据时间动态排序
		map.put("scenicName", scenicName == null ? null : scenicName.trim());
		// 所有列表
		List<MpScenicSpotEntity> list = scenicSpotService.queryList(map);
		// 查询总数
		int toall = scenicSpotService.queryToall(map);
		PageUtils pageUtils = new PageUtils(list, toall, limit, page);
		return R.ok().put("page", pageUtils);
	}

	/**
	 * 删除景区
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("scenic:spot:delete")
	@Transactional
	public R delete(long[] id) {		
		scenicSpotService.deletePath(id);
		return R.ok();
	}

	/**
	 * 保存
	 * 
	 * @param scenic
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions("scenic:spot:save")
	public R save(MpScenicSpotEntity scenic) {		
		scenic.setCreateTime(DateUtils.getCurrentUnixTime());
		scenic.setUpdateTime(DateUtils.getCurrentUnixTime());
		verifyFormSave(scenic);
		scenicSpotService.save(scenic);
		return R.ok();
	}

	/**
	 * 验证
	 * 
	 * @param scenicEntity
	 */
	public void verifyFormSave(MpScenicSpotEntity scenicEntity) {
		if (StringUtils.isBlank(scenicEntity.getScenicName())) {
			throw new RRException("景区名称不能为空");
		} else if (StringUtils.isBlank(scenicEntity.getContent())) {
			throw new RRException("景区详细介绍不能为空");
		} else if (StringUtils.isBlank(scenicEntity.getThumbUrl())) {
			throw new RRException("景区缩略图不能为空");
		} else if (StringUtils.isBlank(scenicEntity.getAbout())) {
			throw new RRException("景区简介不能为空");
		}
		if(scenicEntity.getId()!=null&&scenicEntity.getId()!=0L){
			MpScenicSpotEntity entity=scenicSpotService.queryObject(scenicEntity.getId());
			if(scenicEntity.getScenicName()!=entity.getScenicName()&&!scenicEntity.getScenicName().equals(entity.getScenicName())){
				int total=scenicSpotService.queryByName(scenicEntity.getScenicName());
				if(total>0){
				throw new RRException("该景区已存在");
				}
			}
		}else{
			int total=scenicSpotService.queryByName(scenicEntity.getScenicName());
			if(total>0){
			throw new RRException("该景区已存在");
			}
		}
		
	}

	/**
	 * 修改
	 * @param scenicEntity
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("scenic:spot:update")
	public R update(MpScenicSpotEntity scenicEntity) {
		scenicEntity.setUpdateTime(DateUtils.getCurrentUnixTime());
		verifyFormSave(scenicEntity);
		scenicSpotService.update(scenicEntity);
		return R.ok();
	}

	/**
	 * 根据id查看
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("scenic:spot:info")
	public R info(@PathVariable("id") long id) {
		MpScenicSpotEntity entity = scenicSpotService.queryObject(id);
		return R.ok().put("entity", entity);
	}

	/**
	 * 验证景区名称是否存在
	 * @param scenicName
	 * @return
	 */
	@RequestMapping("/infoName")
	@ResponseBody
	public R infoName(String scenicName) {
		Integer entity = scenicSpotService.queryByName(scenicName);
		if (entity == null || entity ==0) {
			return R.ok();
		}
		return R.error("景区名称已存在");
	}
	/**
	 * 根据景区Id判断是否存在子项景点
	 * @param ids
	 * @return
	 */
	@RequestMapping("/infoScenery")
	@ResponseBody
	public R infoScenery(long[] ids){
		int entityAll = scenerySpotService.queryObjectAll(ids);
		if(entityAll >0){
			return R.error("该景区有子项,不能删除，请先删子项景点");
		}
		return R.ok();
	}
}
