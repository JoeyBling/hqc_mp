package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpOrderRecordsEntity;
import com.hqc.service.MpUpdatesOrdersAndIntegralRecordsService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;

@Controller
@RequestMapping("/octopus/orderUpdate/updatesOrdersAndIntegral")
public class MpUpdatesOrdersAndIntegralRecordsController {
	@Resource
	MpUpdatesOrdersAndIntegralRecordsService updatesOrdersAndIntegralRecordsService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("orderUpdate:updatesOrdersAndIntegral:list")
	@ResponseBody
	public R list(Integer page, Integer checkpage, Integer limit,String itemCode) {
		if (checkpage != null) {
			if (checkpage == 1) {
				page = page - 1;
			}
			if (checkpage == 2) {
				page = page + 1;
			}
		}
		System.out.println(limit);
		Map<String, Object> map = new HashMap<>();
		map.put("itemCode", itemCode==null?null:itemCode.trim());
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "id desc"); 

		List<Map<String, Object>> list = updatesOrdersAndIntegralRecordsService.queryOrderyList(map);
		int total = updatesOrdersAndIntegralRecordsService.queryOrderTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 修改状态
	 */
	@RequestMapping("/update")
	@RequiresPermissions("orderUpdate:updatesOrdersAndIntegral:update")
	@ResponseBody
	@Transactional
	public R update(MpOrderRecordsEntity orderEntity){
		updatesOrdersAndIntegralRecordsService.updateOrderStatus(orderEntity);
		if(orderEntity.getType()==2||orderEntity.getType()==3){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("useStatus", 1);
			map.put("orderNo", orderEntity.getOrderNo());
			map.put("id", orderEntity.getId());
			updatesOrdersAndIntegralRecordsService.updateExchangeRecords(map);
		}
		return R.ok();
		
	}
	

}
