package com.hqc.controller.octopus;

import java.text.ParseException;
import java.util.Date;
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

import com.hqc.entity.MpBannerEntity;
import com.hqc.service.MpBannerService;
import com.hqc.util.Constant;
import com.hqc.util.DateUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 后台横幅图片操作类
 * 
 * @author cxw
 * @date 2017年6月7日
 */
@RestController
@RequestMapping("/octopus/banner")
public class MpBannerController extends AbstractController {

	@Resource
	private MpBannerService mpBannerService;

	/**
	 * 列表页面获取参数
	 * 
	 * @param page
	 * @param checkpage
	 * @param limit
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("list")
	@RequiresPermissions("banner:banner:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) {

		Integer status = Integer.valueOf(request.getParameter("status"));
		String title = request.getParameter("title");

		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isBlank(title)) {
			map.put("title", title);
		}
		if (status != 0) {
			map.put("status", status);
		}

		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		// 查询列表数据
		List<MpBannerEntity> list = mpBannerService.queryList(map);
		int total = mpBannerService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 查询单个（打开修改页面）
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("banner:banner:update")
	public R info(@PathVariable("id") Long id, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		MpBannerEntity banner = mpBannerService.queryObject(id);
		return R.ok().put("banner", banner);
	}

	@RequestMapping("/save")
	@RequiresPermissions("banner:banner:add")
	public R save(MpBannerEntity banner, HttpServletRequest request) {
		validation(banner);
		if (!banner.getUrl().startsWith("http://")) {
			String url = "http://" + banner.getUrl();
			banner.setUrl(url);
		}
		String imgUrl = Constant.getNetAddress() + banner.getThumbUrl();
		banner.setThumbUrl(imgUrl);
		// 以防万一
		this.mpBannerService.save(banner);
		return R.ok();
	}

	/**
	 * 修改
	 * 
	 * @param banner
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/update")
	@RequiresPermissions("banner:banner:update")
	public R update(MpBannerEntity banner, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		validation(banner);
		if (banner.getId() == null) {
			return R.error("修改失败，请刷新页面");
		}
		if (!banner.getThumbUrl().startsWith(Constant.getNetAddress())) {
			String imgUrl = Constant.getNetAddress() + banner.getThumbUrl();
			banner.setThumbUrl(imgUrl);
		}
		if (!banner.getUrl().startsWith("http://")) {
			String url = "http://" + banner.getUrl();
			banner.setUrl(url);
		}
		this.mpBannerService.updateByPrimaryKey(banner);
		return R.ok();
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@RequiresPermissions("banner:banner:del")
	public R del(String ids) {
		if (StringUtils.isBlank(ids)) {
			return R.error("请至少选择一条记录");
		}
		long[] parkingIds = new long[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			parkingIds[i] = Long.valueOf(ids.split(",")[i]);
		}
		this.mpBannerService.deleteBatch(parkingIds);

		// 记录日志
		logger.info("\n删除横幅图片：\n操作人：" + getAdmin().getUsername() + "\n图片Id："
				+ ids + "\n时间" + DateUtils.format(new Date()));

		return R.ok();
	}

	/**
	 * 修改图片状态
	 * 
	 * @param id
	 * @param status
	 * @param request
	 * @return
	 */
	@RequestMapping("/auditStatus")
	public R auditStatus(Long id, Integer status, HttpServletRequest request) {
		MpBannerEntity banner = new MpBannerEntity();
		banner.setId(id);
		banner.setStatus(status);
		this.mpBannerService.updateByPrimaryKey(banner);
		return R.ok();
	}

	public void validation(MpBannerEntity banner) {
		if (StringUtils.isBlank(banner.getTitle())) {
			throw new RRException("请填写图片标题", 500);
		}
		if (StringUtils.isBlank(banner.getThumbUrl())) {
			throw new RRException("请上传图片", 500);
		}
		if (StringUtils.isBlank(banner.getUrl())) {
			throw new RRException("请填写图片相关链接", 500);
		}
	}
}
