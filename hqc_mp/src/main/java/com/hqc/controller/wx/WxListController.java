package com.hqc.controller.wx;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpArticleEntity;
import com.hqc.service.MpArticleService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 微信列表页(酒店餐饮，最新动态)
 * 
 * @author Joey
 * @email:2434387555@qq.com
 * @date：2017年6月1日
 * 
 */
@Controller
@RequestMapping("/wx/list")
public class WxListController extends BaseController {

	private final String hotelfood = "酒店餐饮";
	private final String news = "最新动态";
	private final String trafficWay = "交通路线";
	private final String parkAbout = "园区介绍";
	private final String aboutHqc = "关于华侨城";

	@Resource
	private MpArticleService mpArticleService;

	/**
	 * (酒店餐饮或最新动态)所有信息
	 * 
	 * @return Map
	 */
	@RequestMapping("/{typeName}")
	@ResponseBody
	public R hotel(@PathVariable("typeName") String typeName, Integer page) {
		List<MpArticleEntity> list = null;
		int limit = 5; // 每页显示5条
		int total = 0;
		if ("hotelfood".equals(typeName)) {
			list = mpArticleService.queryAll(hotelfood, limit, (page - 1)
					* limit);
			total = mpArticleService.queryTotalByCategoryName(hotelfood);
		} else if ("news".equals(typeName)) {
			list = mpArticleService.queryAll(news, limit, (page - 1) * limit);
			total = mpArticleService.queryTotalByCategoryName(news);
		}
		PageUtils pageutil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageutil);
	}

	/**
	 * (酒店餐饮或最新动态)详细
	 * 
	 * @param categoryId
	 *            分类ID
	 * @param type
	 *            (1交通路线2园区介绍3关于华侨城)
	 * @return Map
	 */
	@RequestMapping("/detail")
	public String hotelDetail(
			@RequestParam(name = "id", required = false) Long articleId,
			Integer type, Model model) {
		if (null != type) {
			if (type == 1) {
				// 根据类别名称(交通路线)查询所有
				List<MpArticleEntity> trafficWayList = mpArticleService
						.queryTitle(trafficWay);
				if (null == trafficWayList || trafficWayList.size() < 1) {
					return "/wx/404.ftl";
				}
				mpArticleService.updateRead(trafficWayList.get(0).getId());
				model.addAttribute("entity", trafficWayList.get(0));
			} else if (type == 2) {
				// 根据类别名称(园区介绍)查询所有
				List<MpArticleEntity> parkAboutList = mpArticleService
						.queryTitle(parkAbout);
				if (null == parkAboutList || parkAboutList.size() < 1) {
					return "/wx/404.ftl";
				}
				mpArticleService.updateRead(parkAboutList.get(0).getId());
				model.addAttribute("entity", parkAboutList.get(0));
			} else if (type == 3) {
				// 根据类别名称(园区介绍)查询所有
				List<MpArticleEntity> parkAboutList = mpArticleService
						.queryTitle(aboutHqc);
				if (null == parkAboutList || parkAboutList.size() < 1) {
					return "/wx/404.ftl";
				}
				mpArticleService.updateRead(parkAboutList.get(0).getId());
				model.addAttribute("entity", parkAboutList.get(0));
			}
		} else {
			mpArticleService.updateRead(articleId);
			MpArticleEntity entity = mpArticleService.queryObject(articleId);
			if (null == entity) {
				return "/wx/404.ftl";
			}
			model.addAttribute("entity", entity);
		}
		return "/wx/play/detail.ftl";
	}

	/**
	 * 点赞增加
	 * 
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/addLike")
	@ResponseBody
	public R addLike(long articleId) {
		mpArticleService.updateLike(articleId);
		return R.ok();
	}
}
