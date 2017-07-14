package com.hqc.controller.octopus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpParkingcChargeEntity;
import com.hqc.service.MpParkingcChargeService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 停车消费记录相关视图
 * 
 * @author cxw
 * @date 2017年5月15日
 */
@RestController
@RequestMapping("/octopus/parking")
public class MpParkingcChargeController extends AbstractController {

	@Resource
	private MpParkingcChargeService mpParkingcChargeService;

	@RequestMapping("/list")
	@RequiresPermissions("parking:charge:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) throws NumberFormatException, ParseException {
		// 获取条件参数
		String plate = request.getParameter("plate");
		String trueName = request.getParameter("trueName");
		String status = request.getParameter("status");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Map<String, Object> map = new HashMap<>();
		// 条件放入
		map.put("plate", plate);
		map.put("trueName", trueName);
		map.put("status", Integer.valueOf(status));
		if (!StringUtils.isBlank(startTime)) {
			map.put("startTime", JoeyUtil.stampDate(sdf.parse(startTime),
					DateUtils.DATE_TIME_PATTERN));
		}
		if (!StringUtils.isBlank(endTime)) {
			map.put("endTime", JoeyUtil.stampDate(sdf.parse(endTime),
					DateUtils.DATE_TIME_PATTERN));
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
		List<MpParkingcChargeEntity> list = mpParkingcChargeService
				.queryList(map);
		int total = mpParkingcChargeService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 删除记录
	 * 
	 * @return
	 */
	@RequestMapping("/delParkingCharge")
	@RequiresPermissions("parking:charge:delete")
	public R delParkingCharge(String ids) {
		if (StringUtils.isBlank(ids)) {
			return R.error("请至少选择一条记录");
		}
		long[] parkingIds = new long[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			parkingIds[i] = Long.valueOf(ids.split(",")[i]);
		}
		this.mpParkingcChargeService.deleteBatch(parkingIds);

		// 记录日志
		logger.info("\n删除停车订单：\n操作人：" + getAdmin().getUsername() + "\n订单Id："
				+ ids + "\n时间" + DateUtils.format(new Date()));

		return R.ok();
	}
}
