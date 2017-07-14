package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.service.MpArticleCategoryService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

@RestController
@RequestMapping("/octopus/sys/infomationKind")
public class MpArticleCategoryController extends AbstractController {

	@Resource
	private MpArticleCategoryService informationKindService;

	/**
	 * 所有资讯分类
	 */
	@RequestMapping("/list")
	@RequiresPermissions("article:category:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) {
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
		map.put("orderby", "`create_time` desc"); // 排序
		// 查询列表数据
		List<MpArticleCategoryEntity> list = informationKindService
				.queryList(map);
		int total = informationKindService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息分类信息
	 */
	@RequestMapping("/info/{articleId}")
	@RequiresPermissions("article:category:list")
	public R info(@PathVariable("articleId") Long articleId) {
		MpArticleCategoryEntity entity = informationKindService
				.queryObject(articleId);

		return R.ok().put("entity", entity);
	}

	/**
	 * 删除分类
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("article:category:delete")
	public R delete(String infoId) {
		if (StringUtils.isBlank(infoId)) {
			return R.error("删除的管理类别为空");
		}
		int sum = informationKindService.deleteBatch(infoId);
		if (sum == 0) {
			return R.error("当前选择的类别下存在文章");
		}
		if (sum == 2) {
			return R.error("当前选择的类别下存在子类资讯信息类别,请先删除子类信息");
		}
		return R.ok();
	}

	/**
	 * 保存信息资讯
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("article:category:save")
	public R save(MpArticleCategoryEntity articleEntity) throws Exception {
		articleEntity.setId(articleEntity.getId());
		articleEntity.setCategoryName(articleEntity.getCategoryName());
		;
		articleEntity.setCategoryType(articleEntity.getCategoryType());
		;
		articleEntity.setIsSystem(0L);
		;
		Long zz = articleEntity.getParentId();
		if (zz == null) {
			zz = (long) 0;
		}
		int exit=informationKindService.queryByName(articleEntity.getCategoryName().trim());
		System.out.println("lenfds"+exit);
		articleEntity.setParentId(zz);
		if(exit==0)
		{
			informationKindService.save(articleEntity);
		}else
		{
			return R.error("类别名称不可重复");
		}
		return R.ok();
	}

	/**
	 * jiaza
	 */
	@RequestMapping("/xiala")
	@RequiresPermissions("article:category:list")
	public R xiala() {
		List<MpArticleCategoryEntity> entity = informationKindService
				.querybyparent();
		return R.ok().put("entity", entity);
	}

	@RequestMapping("/update")
	@RequiresPermissions("article:category:update")
	public R update(MpArticleCategoryEntity entity, HttpServletRequest request) {
		if (entity.getParentId() == null) {
			entity.setParentId(0L);
		}
		informationKindService.update(entity);
		return R.ok();
	}
}
