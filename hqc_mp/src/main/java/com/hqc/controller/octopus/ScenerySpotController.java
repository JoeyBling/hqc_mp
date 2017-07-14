package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.entity.MpScenicSpotEntity;
import com.hqc.service.ProjectMpService;
import com.hqc.service.ScenerySpotService;
import com.hqc.util.DateUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 景点管理后台控制器
 * 
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月15日
 */
@Controller
@RequestMapping("/octopus/scenerySpot/scenery")
public class ScenerySpotController {

	@Resource
	private ScenerySpotService scenerySpotService;

	@Resource
	private ProjectMpService projectMpService;

	/**
	 * 所有景点列表
	 * 
	 * @param page
	 * @param limit
	 * @param orderby
	 * @return
	 */

	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions("scenery:spot:list")
	public R list(String sceneryName, Integer page, Integer limit,
			Integer checkpage, String orderby) {
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
		map.put("sceneryName", sceneryName == null ? null : sceneryName.trim());
		map.put("orderby", "s.create_time desc");// 根据时间动态排序
		// 查询所有
		List<MpScenerySpotEntity> mpScenerySpotList = scenerySpotService
				.queryList(map);
		// 查询总数
		int toall = scenerySpotService.querytoAll(map);

		PageUtils pageUtils = new PageUtils(mpScenerySpotList, toall, limit,
				page);

		return R.ok().put("page", pageUtils);
	}

	/**
	 * 删除景点（多个）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("scenery:spot:delete")
	@Transactional
	public R delete(long[] id) {
		scenerySpotService.deletePath(id);		
		return R.ok();
	}

	/**
	 * 验证
	 * 
	 * @param sceneryEntity
	 * @param scenicEntity
	 */
	public void verifyForm(MpScenerySpotEntity sceneryEntity) {
		if (StringUtils.isBlank(sceneryEntity.getSceneryName())) {
			throw new RRException("景点名称不能为空");
		} else if (StringUtils.isBlank(sceneryEntity.getSceneryContent())) {
			throw new RRException("景点详细介绍不能为空");
		} else if (StringUtils.isBlank(sceneryEntity.getThumbUrl())) {
			throw new RRException("景点缩略图地址不能为空");
		} else if (StringUtils.isBlank(sceneryEntity.getAbout())) {
			throw new RRException("景点简介不能为空");
		}
		if(sceneryEntity.getId()!=null&&sceneryEntity.getId()!=0L){
			MpScenerySpotEntity entity=scenerySpotService.queryObject(sceneryEntity.getId());
			if(sceneryEntity.getSceneryName()!=entity.getSceneryName()&&!sceneryEntity.getSceneryName().equals(entity.getSceneryName())){
				int total=scenerySpotService.queryByName(sceneryEntity.getSceneryName());
				if(total>0){
				throw new RRException("该景点已存在");
				}
			}
		}else{
			int total=scenerySpotService.queryByName(sceneryEntity.getSceneryName());
			if(total>0){
			throw new RRException("该景点已存在");
			}
		}
	}

	/**
	 * 查询所属景区
	 * 
	 * @return
	 */
	@RequestMapping("/getscenic")
	@ResponseBody
	@RequiresPermissions("scenery:spot:list")
	public R list() {
		List<MpScenicSpotEntity> scenicList = scenerySpotService
				.queryListscenic();
		return R.ok().put("scenicList", scenicList);
	}

	/**
	 * 保存
	 * 
	 * @param sceneryEntity
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	@RequiresPermissions("scenery:spot:save")
	public R save(MpScenerySpotEntity sceneryEntity) {
		sceneryEntity.setUpdateTime(DateUtils.getCurrentUnixTime());
		sceneryEntity.setCreateTime(DateUtils.getCurrentUnixTime());
		verifyForm(sceneryEntity);
		scenerySpotService.save(sceneryEntity);
		return R.ok();
	}

	/**
	 * 修改
	 * 
	 * @param scenicEntity
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("scenery:spot:update")
	public R update(MpScenerySpotEntity sceneryEntity){
		sceneryEntity.setUpdateTime(DateUtils.getCurrentUnixTime());
		verifyForm(sceneryEntity);
		scenerySpotService.update(sceneryEntity);
		return R.ok();
	}

	/**
	 * 根据id查询景点和景区
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("scenery:spot:info")
	public R info(@PathVariable("id") long id) {
		MpScenerySpotEntity entity = scenerySpotService.queryObject(id);
		MpScenicSpotEntity scenicEntity = scenerySpotService
				.queryscenicById(entity.getScenicId());
		entity.setMpScenicSpotEntity(scenicEntity);
		return R.ok().put("entity", entity);
	}

	/**
	 * 验证景点名称是否存在
	 * 
	 * @param sceneryName
	 * @return
	 */
	@RequestMapping("/infoName")
	@ResponseBody
	public R infoName(String sceneryName) {
		Integer entity = scenerySpotService.queryByName(sceneryName);
		if (entity == null || entity ==0) {
			return R.ok();
		}
		return R.error("景点名称已存在");
	}
	/**
	 * 根据景点Id判断是否存在子项景点项目
	 * @param ids
	 * @return
	 */
	@RequestMapping("/infoProject")
	@ResponseBody
	public R infoScenery(long[] ids){
		int entityAll = projectMpService.queryObjectAll(ids);
		if(entityAll >0){
			return R.error("该景点有子项,不能删除，请先删子项景点项目");
		}
		return R.ok();
	}
}
