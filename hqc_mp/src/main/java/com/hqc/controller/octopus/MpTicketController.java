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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpTicketEntity;
import com.hqc.service.MpTicketService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 门票后台相关视图
 * 
 * @author cxw
 * @date 2017年6月5日
 */
@RestController
@RequestMapping("/octopus/ticket")
public class MpTicketController extends AbstractController {

	@Resource
	private MpTicketService mpTicketService;

	/**
	 * 查询列表
	 * 
	 * @param page
	 * @param checkpage
	 * @param limit
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/list")
	@RequiresPermissions("ticket:ticket:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) throws NumberFormatException,
			ParseException {
		//获取查询条件参数
		String ticketName = request.getParameter("ticketName");
		String minPrice = request.getParameter("minPrice");
		String maxPrice = request.getParameter("maxPrice");
		String status = request.getParameter("status");
		
		Map<String, Object> map = new HashMap<>();
		//条件有值就查询
		if(!StringUtils.isBlank(ticketName)){
			map.put("ticketName", ticketName);
		}
		if(!StringUtils.isBlank(minPrice)){
			map.put("minPrice", Double.valueOf(minPrice));
		}
		if(!StringUtils.isBlank(maxPrice)){
			map.put("maxPrice", Double.valueOf(maxPrice));
		}
		if(!StringUtils.isBlank(status)){
			map.put("status", Integer.valueOf(status));
		}

		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		map.put("orderby", "status,update_time desc");
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		List<MpTicketEntity> list = this.mpTicketService.queryList(map);
		Integer total = this.mpTicketService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 保存，添加！
	 * 
	 * @param ticket
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/save")
	@RequiresPermissions("ticket:ticket:save")
	public R save(MpTicketEntity ticket, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		validation(ticket,request);
		ticket.setDiscount(ticket.getPrice()/ticket.getMarketPrice());
		ticket.setCreateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		ticket.setSaleCount((long) 0);
		ticket.setUpdateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		this.mpTicketService.save(ticket);
		return R.ok();
	}

	/**
	 * 查询单个
	 * 
	 * @param id
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("ticket:ticket:update")
	public R info(@PathVariable("id") Long id, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		MpTicketEntity ticket = mpTicketService.queryObject(id);
		return R.ok().put("ticket", ticket);
	}
	
	/**
	 * 修改
	 * @param ticket
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/update")
	@RequiresPermissions("ticket:ticket:update")
	public R update(MpTicketEntity ticket, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		validation(ticket,request);
		if (ticket.getId() == null) {
			return R.error("修改失败，请刷新页面");
		}
		ticket.setDiscount(ticket.getPrice()/ticket.getMarketPrice());
		ticket.setUpdateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		this.mpTicketService.updateByPrimaryKey(ticket);
		return R.ok();
	}
	
	/**
	 * 删除多个
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("ticket:ticket:del")
	public R delete(String ids, HttpServletRequest request) {
		if (StringUtils.isBlank(ids)) {
			return R.error("请至少选择一条记录");
		}
		long[] ticketIds = new long[ids.split(",").length];
		for (int i = 0; i < ids.split(",").length; i++) {
			ticketIds[i] = Long.valueOf(ids.split(",")[i]);
		}
		this.mpTicketService.deleteBatch(ticketIds);

		// 记录日志
		logger.info("\n执行删除门票：\n操作人：" + getAdmin().getUsername() + "\n门票Id："
				+ ids + "\n时间" + DateUtils.format(new Date()));

		return R.ok();
	}
	
	/**
	 * 修改门票状态
	 * @param id
	 * @param status
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	@RequestMapping("/auditStatus")
	public R auditStatus(Long id, Integer status, HttpServletRequest request) throws NumberFormatException, ParseException {
		MpTicketEntity ticket = new MpTicketEntity();
		ticket.setId(id);
		ticket.setStatus(status);
		ticket.setUpdateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		this.mpTicketService.updateByPrimaryKey(ticket);
		return R.ok();
	}

	/**
	 * 验证对象是否有值（添加和修改用）,包括三个时间的值
	 * 
	 * @param ticket
	 * @return
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public boolean validation(MpTicketEntity ticket,HttpServletRequest request) throws NumberFormatException, ParseException {
		String format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String startBuyDateString = request.getParameter("startBuyDateString");
		String endBuyDateString = request.getParameter("endBuyDateString");
		String saleDateString = request.getParameter("saleDateString");
		long saleDate = 0;
		long endBuyDate = 0;
		long startBuyDate = 0;
		if( StringUtils.isBlank(saleDateString)){
			throw new RRException("请输入开售时间", 500);
		}else {
			saleDate = JoeyUtil.stampDate(sdf.parse(saleDateString),DateUtils.DATE_TIME_PATTERN);
			ticket.setSaleDate(saleDate);
		}
		if( StringUtils.isBlank(startBuyDateString)){
			throw new RRException("请输入使用开始时间", 500);
		}else {
			startBuyDate = JoeyUtil.stampDate(sdf.parse(startBuyDateString),DateUtils.DATE_TIME_PATTERN);
			ticket.setStartBuyDate(startBuyDate);
		}
		if( StringUtils.isBlank(endBuyDateString)){
			throw new RRException("请输入使用结束时间", 500);
		}else {
			endBuyDate = JoeyUtil.stampDate(sdf.parse(endBuyDateString),DateUtils.DATE_TIME_PATTERN);
			ticket.setEndBuyDate(endBuyDate);
		}
		if(startBuyDate > endBuyDate){
			throw new RRException("开始时间不能大于结束时间", 500);
		}
		if(saleDate > startBuyDate){
			throw new RRException("使用时间必须大于开售时间", 500);
		}
		if (ticket.getWeekendPrice() ==null) {
			ticket.setWeekendPrice( 0.0);
		}
		
		if (ticket.getWeekendType() == true && (ticket.getWeekendPrice() ==null || ticket.getWeekendPrice() ==0)) {
			throw new RRException("请输入周末价格", 500);
		}
		if (ticket.getWeekendType() == true &&(ticket.getWeekendPrice() == null || ticket.getWeekendPrice() <= 0)) {
			throw new RRException("请正确输入周末价格", 500);
		}
		if (StringUtils.isBlank(ticket.getTicketName())) {
			throw new RRException("请输入门票名称", 500);
		}
		if (StringUtils.isBlank(ticket.getThumbUrl())) {
			throw new RRException("请上传门票图片", 500);
		}
		if (ticket.getPrice() == null || ticket.getPrice() <= 0) {
			throw new RRException("请输入正确价格", 500);
		}
		if (ticket.getMarketPrice() <= ticket.getPrice() ) {
			throw new RRException("单价不能大于市场假", 500);
		}
		if (ticket.getMarketPrice() == null || ticket.getMarketPrice() <= 0) {
			throw new RRException("请输入正确市场价", 500);
		}
		if (StringUtils.isBlank(ticket.getAbout())) {
			throw new RRException("请填写预定须知", 500);
		}
		if (StringUtils.isBlank(ticket.getTicketContent())) {
			throw new RRException("请填写门票详情", 500);
		}
		return true;
	}
}
