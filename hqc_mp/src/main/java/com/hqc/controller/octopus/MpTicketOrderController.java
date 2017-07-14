package com.hqc.controller.octopus;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import com.hqc.entity.MpTicketOrderEntity;
import com.hqc.service.MpTicketOrderService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

/**
 * 后台门票订单查询相关视图
 * 
 * @author cxw
 * @date 2017年6月6日
 */
@RestController
@RequestMapping("/octopus/ticketOrder")
public class MpTicketOrderController extends AbstractController{
	
	@Resource
	private MpTicketOrderService mpTicketOrderService;
	
	/**
	 * 查询门票记录（统计用）
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping("list")
	@RequiresPermissions("ticket:order:list")
	public R list(Integer page, Integer checkpage, Integer limit,
					HttpServletRequest request) throws ParseException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		//获取参数
		String endTime = request.getParameter("endTime");
		String startTime = request.getParameter("startTime");
		String status = request.getParameter("status");
		String orderPhone = request.getParameter("orderPhone");
		String ticketName = request.getParameter("ticketName");
		String orderNo = request.getParameter("orderNo");
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Map<String , Object> map = new HashMap<String, Object>();
		
		//判断非空传条件
		if( !StringUtils.isBlank(endTime)){
			map.put("endTime", JoeyUtil.stampDate(sdf.parse(endTime),
					DateUtils.DATE_TIME_PATTERN));
		}
		if( !StringUtils.isBlank(startTime)){
			map.put("startTime", JoeyUtil.stampDate(sdf.parse(startTime),
					DateUtils.DATE_TIME_PATTERN));
		}
		if( !StringUtils.isBlank(status)){
			map.put("status", Integer.valueOf(status));
		}
		if( !StringUtils.isBlank(orderPhone)){
			map.put("orderPhone", orderPhone);
		}
		if( !StringUtils.isBlank(ticketName)){
				map.put("ticketName", ticketName);
		}
		if( !StringUtils.isBlank(orderNo)){
				map.put("orderNo", orderNo);
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
		map.put("del", 0);
		List<MpTicketOrderEntity> list = this.mpTicketOrderService.queryList(map);
		Integer total = this.mpTicketOrderService.queryTotal(map);
		//测试导出表格
//		Map data = new HashMap();
//		data.put("listData", list);    
//		Map<String, String > ziDuan = ExcelUtil.getTicketOrderZiduan();
//		data.put("ziDuan", ziDuan);
//		ExcelUtil.objListToExcel("门票订单.xls", data);
		
		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 删除记录
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	@RequiresPermissions("ticket:order:del")
	public R del(String ids) {
		if (StringUtils.isBlank(ids)) {
			return R.error("请至少选择一条记录");
		}
		long[] parkingIds = new long[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			parkingIds[i] = Long.valueOf(ids.split(",")[i]);
		}
		this.mpTicketOrderService.deleteBatch(parkingIds);

		// 记录日志
		logger.info("\n删除购票订单：\n操作人：" + getAdmin().getUsername() + "\n订单Id："
				+ ids + "\n时间" + DateUtils.format(new Date()));

		return R.ok();
	}
}
