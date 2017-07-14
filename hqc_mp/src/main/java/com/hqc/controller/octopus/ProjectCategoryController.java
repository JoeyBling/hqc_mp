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

import com.hqc.entity.MpProjectCategoryEntity;
import com.hqc.service.ProjectCategoryService;
import com.hqc.service.ProjectMpService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 项目类型管理后台
 * 
 * @author Administrator
 * @项目hqc_mp
 * @创建人
 * @创建时间 2017年5月19日
 */
@RestController
@RequestMapping("/octopus/category")
public class ProjectCategoryController {

	@Resource
	private ProjectCategoryService projectCategoryService;
	
	@Resource
	private ProjectMpService projectService;

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
	@RequiresPermissions("project:category:list")
	public R list(Integer page, Integer limit, Integer checkpage,Integer systems,String categoryName ) {
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
		map.put("systems", systems);
		map.put("categoryName", categoryName == null ? null:categoryName.trim());
		// 所有列表
		List<MpProjectCategoryEntity> list = projectCategoryService
				.queryList(map);
		// 查询总数
		int toall = projectCategoryService.queryToall(map);

		PageUtils pageUtils = new PageUtils(list, toall, limit, page);

		return R.ok().put("page", pageUtils);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("project:category:delete")
	@Transactional
	public R delete(long[] id) {
		List<MpProjectCategoryEntity> list = projectCategoryService
				.queryByIds(id);
		if (list.size() != 0) {
			int isytem = 0;
			for (int i = 0; i < list.size(); i++) {
				isytem = list.get(i).getIsSystem();
				if (isytem == 1) {
					return R.error("系统内置分类不能删除");

				} else {
					projectCategoryService.deletePath(id);
				}

			}
		}
		return R.ok();
	}

	/**
	 * 保存
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions("project:category:save")
	@ResponseBody
	public R save(MpProjectCategoryEntity category, Long categoryId) {
		category.setParentId(categoryId);
		verifyForm(category);
		projectCategoryService.save(category);
		return R.ok();
	}
	/**
	 * 修改
	 * 
	 * @param category
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("project:category:update")
	public R update(MpProjectCategoryEntity category, Long categoryId) {		
		category.setParentId(categoryId);
		verifyForm(category);
		projectCategoryService.update(category);
		return R.ok();
	}

	/**
	 * 根据id查询修改信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("project:category:info")
	public R info(@PathVariable("id") long id) {
		MpProjectCategoryEntity entity = projectCategoryService.queryObject(id);
		return R.ok().put("entity", entity);
	}

	/**
	 * 查询父类项目
	 * @return
	 */
	@RequestMapping("/getproCategory")
	@ResponseBody
	@RequiresPermissions("project:category:list")
	public R getproCategory() {
		List<MpProjectCategoryEntity> proCategoryList = projectCategoryService
				.queryListproCategory();
		return R.ok().put("proCategoryList", proCategoryList);
	}
	/**
	 * 验证项目类型是否已存在
	 * @param categoryName
	 * @return
	 */
	@RequestMapping("/infoName")
	@ResponseBody
	public R infoName(String categoryName){		
			Integer entity = projectCategoryService.queryByName(categoryName);
			if(entity == null || entity ==0){				
				return R.ok();				
			}		
		return R.error("项目类型已存在");
	}
	/**
	 * 根据项目类型Id判断是否存在子项景点
	 * @param ids
	 * @return
	 */
	@RequestMapping("/infoCategory")
	@ResponseBody
	public R infoScenery(long[] ids){
		int entityAll = projectService.queryCategorytAll(ids);
		if(entityAll >0){
			return R.error("该项目类型有子项,不能删除，请先删子项景点项目");
		}
		return R.ok();
	}
	
	/**
	 * 验证
	 * 
	 * @param scenicEntity
	 */
	public void verifyForm(MpProjectCategoryEntity categoryEntity) {
		if (StringUtils.isBlank(categoryEntity.getCategoryName())) {
			throw new RRException("项目类型不能为空");
		}
		if(categoryEntity.getId()!=null&&categoryEntity.getId()!=0L){
			MpProjectCategoryEntity entity=projectCategoryService.queryObject(categoryEntity.getId());
			if(categoryEntity.getCategoryName()!=entity.getCategoryName()&&!categoryEntity.getCategoryName().equals(entity.getCategoryName())){
				int total=projectCategoryService.queryByName(categoryEntity.getCategoryName());
				if(total>0){
				throw new RRException("该项目类型已存在");
				}
			}
		}else{
			int total=projectCategoryService.queryByName(categoryEntity.getCategoryName());
			if(total>0){
			throw new RRException("该项目类型已存在");
			}
		}
	}
	
}
