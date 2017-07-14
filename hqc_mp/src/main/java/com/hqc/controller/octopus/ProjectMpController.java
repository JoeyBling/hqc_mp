package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.entity.MpProjectEntity;
import com.hqc.entity.MpScenerySpotEntity;
import com.hqc.service.ProjectMpService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 景点项目管理后台
 * 
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
@RestController
@RequestMapping("/octopus/projectsss/pro")
public class ProjectMpController {

	@Resource
	private ProjectMpService projectMpService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @param limit
	 * @param checkpage
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions("scenic:project:list")
	public R list(Integer page, Integer limit, Integer checkpage,String projectName,String status) {
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
		map.put("projectName", projectName ==null ? null : projectName.trim());
		map.put("status", status ==null ? null : status.trim());
		List<MpProjectEntity> list = projectMpService.queryList(map);

		int toall = projectMpService.queryToall(map);

		PageUtils pageUtils = new PageUtils(list, toall, limit, page);

		return R.ok().put("page", pageUtils);
	}

	/**
	 * 查询所有项目类型
	 * @return
	 */
	@RequestMapping("/getcategory")
	@ResponseBody
	@RequiresPermissions("scenic:project:list")
	public R categoryList() {
		List<MpProjectCategoryEntity> categoryList = projectMpService
				.queryListCategory();
		return R.ok().put("categoryList", categoryList);
	}

	/**
	 * 查询所有景点
	 * @return
	 */
	@RequestMapping("/getscenery")
	@ResponseBody
	@RequiresPermissions("scenic:project:list")
	public R sceneryList() {
		List<MpScenerySpotEntity> sceneryList = projectMpService
				.queryListScenery();
		return R.ok().put("sceneryList", sceneryList);
	}

	/**
	 * 保存
	 * 
	 * @param projectEntity
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	@RequiresPermissions("scenic:project:save")
	public R save(MpProjectEntity projectEntity) {
		verifyForm(projectEntity);
		projectMpService.save(projectEntity);
		return R.ok();
	}

	/**
	 * 验证
	 * 
	 * @param scenicEntity
	 */
	public void verifyForm(MpProjectEntity projectEntity) {
		if (StringUtils.isBlank(projectEntity.getProjectName())) {
			throw new RRException("项目名称不能为空");
		} else if (StringUtils.isBlank(projectEntity.getProjectContent())) {
			throw new RRException("项目详细介绍不能为空");
		} else if (StringUtils.isBlank(projectEntity.getAbout())) {
			throw new RRException("项目简介不能为空");
		} else if (StringUtils.isBlank(projectEntity.getLat())) {
			throw new RRException("纬度不能为空");
		} else if (StringUtils.isBlank(projectEntity.getLng())) {
			throw new RRException("经度不能为空");
		} else if (StringUtils.isBlank(projectEntity.getThumbUrl())) {
			throw new RRException("项目缩略图不能为空");
		}
		if(projectEntity.getId()!=null&&projectEntity.getId()!=0L){
			MpProjectEntity entity=projectMpService.queryObject(projectEntity.getId());
			if(projectEntity.getProjectName()!=entity.getProjectName()&&!projectEntity.getProjectName().equals(entity.getProjectName())){
				int total=projectMpService.queryByName(projectEntity.getProjectName());
				if(total>0){
				throw new RRException("该项目已存在");
				}
			}
		}else{
			int total=projectMpService.queryByName(projectEntity.getProjectName());
			if(total>0){
			throw new RRException("该项目已存在");
			}
		}
	}

	/**
	 * 修改
	 * 
	 * @param scenicEntity
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("scenic:project:update")
	public R update(MpProjectEntity projectEntity) {		
		verifyForm(projectEntity);
		projectMpService.update(projectEntity);
		return R.ok();
	}

	/**
	 * 根据Id修改
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("scenic:project:info")
	public R info(@PathVariable("id") long id) {

		MpProjectEntity entity = projectMpService.queryObject(id);
		MpScenerySpotEntity sceneryEntity = projectMpService
				.querysceneryById(entity.getSceneryId());
		MpProjectCategoryEntity categoryEntity = projectMpService
				.querycategoryById(entity.getCategoryId());
		entity.setMpScenerySpotEntity(sceneryEntity);
		entity.setMpProjectCategoryEntity(categoryEntity);
		return R.ok().put("entity", entity);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("scenic:project:delete")
	public R delete(long[] id) {
		List<MpProjectEntity> list = projectMpService.queryByIds(id);
		if (list.size() != 0) {
			int status = 0;
			for (int i = 0; i < list.size(); i++) {
				status = list.get(i).getStatus();
				if (status == 1) {
					return R.error("已经在使用不能删除");

				} else {
					projectMpService.deletePath(id);
				}
			}
		}
		return R.ok();
	}
	/**
	 * 验证项目名称是否已存在
	 * @param projectName
	 * @return
	 */
	@RequestMapping("/infoName")
	@ResponseBody
	public R infoName(String projectName){		
			Integer entity = projectMpService.queryByName(projectName);
			if(entity == null || entity ==0){				
				return R.ok();				
			}		
		return R.error("项目名称已存在");
	}

}
