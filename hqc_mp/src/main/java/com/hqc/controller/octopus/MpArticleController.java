package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpArticleCategoryEntity;
import com.hqc.entity.MpArticleEntity;
import com.hqc.service.MpArticleService;
import com.hqc.util.DateUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 图文信息
 * 
 * @author jzh
 * @date 2017年5月15日 上午11:15:10
 */
@RestController
@RequestMapping("/octopus/sys/infomation")
public class MpArticleController extends AbstractController {

	@Resource
	private MpArticleService informationService;

	/**
	 * 所有图文信息列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("article:info:list")
	public R list(Integer page, Integer limit, Integer checkpage,
			HttpServletRequest request) {
		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String categoryName = request.getParameter("categoryName");
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("categoryName",
				categoryName == null ? null : categoryName.trim());
		map.put("author", author == null ? null : author.trim());
		map.put("title", title == null ? null : title.trim());
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "`create_time` desc"); // 排序
		// 查询列表数据
		List<MpArticleEntity> list = informationService.queryList(map);
		int total = informationService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息资讯信息
	 */
	@RequestMapping("/info/{articleId}")
	@RequiresPermissions("article:info:info")
	public R info(@PathVariable("articleId") Long articleId) {
		MpArticleEntity entity = informationService.queryObject(articleId);
		return R.ok().put("entity", entity);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("article:info:delete")
	public R delete(String ids) {

		long[] infos = new long[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			infos[i] = Long.valueOf(ids.split(",")[i]);
		}
		informationService.deleteBatch(infos);

		return R.ok();
	}

	/**
	 * 保存信息资讯
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions("article:info:save")
	public R save(MpArticleEntity articleEntity) throws Exception {
		if (articleEntity.getAuthor() == null
				|| articleEntity.getAuthor() == "") {
			return R.error("作者名称不能为空");
		}
		if (articleEntity.getTitle() == null || articleEntity.getTitle() == "") {
			return R.error("标题不能为空");
		}
		if (articleEntity.getCategoryId() == null) {
			return R.error("请先添加图文信息类别");
		}
		validData(articleEntity);
		articleEntity.setUserId(getAdminId());
		articleEntity.setCreateTime(DateUtils.getCurrentUnixTime());
		articleEntity.setLikeCount(0L);
		articleEntity.setReadCount(0L);
		articleEntity.setStatus(1); // 默认状态为正常状态
		informationService.save(articleEntity);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("article:info:update")
	@ResponseBody
	public R update(MpArticleEntity entity) {
		MpArticleEntity temEntity = informationService.queryObject(entity.getId());
		if (entity.getCategoryId() != temEntity.getCategoryId()) {
			validData(entity);
		}
		informationService.update(entity);
		return R.ok();
	}

	/**
	 * 得到所有图文信息分类
	 */
	@RequestMapping("getAllcategory")
	@ResponseBody
	@RequiresPermissions("article:info:list")
	public R getAllcategory() {
		List<MpArticleCategoryEntity> categoryList = informationService
				.queryArticleCategory();
		return R.ok().put("categoryList", categoryList);
	}

	/**
	 * 验证数据
	 */
	private void validData(MpArticleEntity entity) {
		int total = informationService.queryArticleTotalByCategory(entity
				.getCategoryId());
		if (total > 0) {
			throw new RRException("该图文信息类为单页只能添加一遍文章（该类别下已有文章）");
		}
	}

}
