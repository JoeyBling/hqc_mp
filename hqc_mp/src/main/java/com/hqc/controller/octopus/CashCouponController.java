package com.hqc.controller.octopus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpCashCouponEntity;
import com.hqc.service.CashCouponService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 代金卷
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/octopus/goods/cashCoupon")
public class CashCouponController {
    @Resource
	private CashCouponService cashCouponService;
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("goods:cashCoupon:list")
	@ResponseBody
	public R list(Integer page, Integer checkpage, Integer limit,String cashCouponName,String status) {
		if (checkpage != null) {
			if (checkpage == 1) {
				page = page - 1;
			}
			if (checkpage == 2) {
				page = page + 1;
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("cashCouponName", cashCouponName==null?null:cashCouponName.trim());
		map.put("status", status);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		map.put("orderby", "id desc"); 

		List<MpCashCouponEntity> list = cashCouponService.queryList(map);
		int total = cashCouponService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 验证数据
	 */
	private void validData(MpCashCouponEntity entity){
		if (StringUtils.isBlank(entity.getCashCouponName())) {
			throw new RRException("代金券名称不能为空");
		}
		if (entity.getFaceValue()==null||entity.getFaceValue()<=0) {
			throw new RRException("代金券面值不能为空，不能小于等于0");
		}
		if(entity.getIntegral()==null){
			throw new RRException("所需积分不能为空");
		}
	}
	/**
	 * 增加
	 */
	@RequestMapping("/save")
	@RequiresPermissions("goods:cashCoupon:save")
	@ResponseBody
	public R save(MpCashCouponEntity entity){
		validData(entity);
		cashCouponService.insert(entity);
		return R.ok();
	}
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("goods:cashCoupon:update")
	@ResponseBody
	public R update(MpCashCouponEntity entity){
		validData(entity);
		cashCouponService.update(entity);
		return R.ok();
	}
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("goods:cashCoupon:delete")
	@ResponseBody
	public R delete(long[] ids){
		validDelete(ids);
		cashCouponService.delete(ids);
		return R.ok();
	}
	/**
	 * 验证删除
	 */
	private void validDelete(long[] ids){
		String tempId="";
		for(int i=0;i<ids.length;i++){
			int total=cashCouponService.queryUsingCash(ids[i]);
			if(total>0){
				tempId+=ids[i]+",";
			}
		}
		if(tempId!=null&&!"".equals(tempId)){
			throw new RRException("id为："+tempId+" 代金卷已被使用不能删除");
		}
		
	}
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("goods:cashCoupon:info")
	@ResponseBody
	public R info(@PathVariable("id") Long id) {
		MpCashCouponEntity entity = cashCouponService.queryObject(id);

		return R.ok().put("entity", entity);
	}
	/**
	 * 上架 下架
	 */
	@RequestMapping("/auitStatus")
	@ResponseBody
	public R auitStatus(MpCashCouponEntity entity){
		
		cashCouponService.update(entity);
		return R.ok();
	}
}
