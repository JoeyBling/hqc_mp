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
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpGoodsCategoryEntity;
import com.hqc.service.GoodsCategoryService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 商品分类
 * 
 * @author Administrator
 * 
 */
@RestController
@RequestMapping("/octopus/goods/category")
public class GoodsCategoryController extends AbstractController {

	@Resource
	private GoodsCategoryService goodsCategoryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("goods:category:list")
	public R list(Integer page, Integer checkpage, Integer limit) {
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
		map.put("orderby", "id desc"); // 排序

		List<MpGoodsCategoryEntity> list = goodsCategoryService.queryList(map);
		int total = goodsCategoryService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@RequiresPermissions("goods:category:save")
	public R save(MpGoodsCategoryEntity entity) {

		validParams(entity);
		goodsCategoryService.save(entity);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("goods:category:update")
	public R update(MpGoodsCategoryEntity entity) {
		validParams(entity);
		goodsCategoryService.update(entity);
		return R.ok();
	}

	/**
	 * 查找
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("goods:category:info")
	public R info(@PathVariable("id") Long id) {
		MpGoodsCategoryEntity entity = goodsCategoryService.queryObject(id);

		return R.ok().put("entity", entity);
	}

	/**
	 * 验证参数
	 */
	private void validParams(MpGoodsCategoryEntity entity) {
		if (StringUtils.isBlank(entity.getCategoryName())) {
			throw new RRException("商品分类名称不能为空");
		}
		if (entity.getId() != null && entity.getId() > 0) {
			MpGoodsCategoryEntity tempEntity = goodsCategoryService
					.queryObject(entity.getId());
			if (entity.getCategoryName() != tempEntity.getCategoryName()
					&& !entity.getCategoryName().equals(
							tempEntity.getCategoryName())) {
				int total = goodsCategoryService.queryTotalByName(entity);
				if (total > 0) {
					throw new RRException("该商品分类名称已存在");
				}
			}
		} else {
			int total = goodsCategoryService.queryTotalByName(entity);
			if (total > 0) {
				throw new RRException("该商品分类名称已存在");
			}
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("goods:category:delete")
	@Transactional
	public R delete(long[] ids) {
		validDelete(ids);
		for (int i = 0; i < ids.length; i++) {
			goodsCategoryService.deleteGoodsByCategory(ids[i]);
		}
		goodsCategoryService.deleteBatch(ids);
		return R.ok();
	}

	/**
	 * 验证商品分类是否能被删除
	 */
	private void validDelete(long[] ids) {
		String tempId = "";
		for (int i = 0; i < ids.length; i++) {
			int sum = goodsCategoryService.queryUsingCategory(ids[i]);
			if (sum > 0) {
				tempId += ids[i] + ",";
			}
		}
		if (tempId != null && !"".equals(tempId)) {
			throw new RRException("id为：" + tempId + " 商品类别已被使用不能删除");
		}
	}

}
