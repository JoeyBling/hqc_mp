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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hqc.entity.MpGoodsCategoryEntity;
import com.hqc.entity.MpGoodsEntity;
import com.hqc.service.GoodsService;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

/**
 * 商品
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/octopus/goods/goods")
public class GoodsController extends AbstractController {

	@Resource
	private GoodsService goodsService;

	/**
	 * 列表
	 * 
	 * @param page
	 * @param checkpage
	 * @param limit
	 * @param categoryName
	 * @param goodsName
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("goods:goods:list")
	@ResponseBody
	public R list(Integer page, Integer checkpage, Integer limit,
			String categoryName, String goodsName) {
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
		map.put("categoryName",
				categoryName == null ? null : categoryName.trim());
		map.put("goodsName", goodsName == null ? null : goodsName.trim());
		List<MpGoodsEntity> list = goodsService.queryList(map);
		int total = goodsService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 获取商品分类
	 */
	@RequestMapping("/getCategory")
	@ResponseBody
	public R getCategory() {
		List<MpGoodsCategoryEntity> categoryList = goodsService
				.queryAllGategory();
		return R.ok().put("categoryList", categoryList);
	}

	/**
	 * 将时间转化为long
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/toLong/{time}")
	@ResponseBody
	public R toLong(@PathVariable("time") String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date timeDate = sdf.parse(time);
		Long LongData = JoeyUtil.stampDate(timeDate, "yyyy-MM-dd");
		return R.ok().put("LongData", LongData);
	}

	/**
	 * 添加
	 */
	@RequestMapping("/save")
	@RequiresPermissions("goods:goods:save")
	@ResponseBody
	public R save(MpGoodsEntity entity) {

		entity.setUserId(getAdminId());
		validData(entity);
		goodsService.save(entity);
		return R.ok();
	}

	/**
	 * 验证数据
	 */
	private void validData(MpGoodsEntity entity) {
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("goodsName",
				entity.getGoodsName() == null ? null : entity.getGoodsName().trim());
		if (StringUtils.isBlank(entity.getGoodsName())) {
			throw new RRException("商品名称不能为空");
		}
		if (entity.getRepertory() == null || entity.getRepertory() == 0L) {
			throw new RRException("当前库存不能为空");
		}
		if (entity.getPrice() == null) {
			throw new RRException("商品价格不能为空");
		}
		if(entity.getId()!=null&&entity.getId()!=0L){
			MpGoodsEntity tempEntity=goodsService.queryObject(entity.getId());
			if(entity.getGoodsName()!=tempEntity.getGoodsName()&&!entity.getGoodsName().equals(tempEntity.getGoodsName())){
				int total=goodsService.queryGoodsByName(map);
				if(total>0){
				throw new RRException("改商品已存在");
				}
			}
		}else{
			int total=goodsService.queryGoodsByName(map);
			if(total>0){
			throw new RRException("改商品已存在");
			}
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("goods:goods:info")
	@ResponseBody
	public R info(@PathVariable("id") Long id) {
		MpGoodsEntity entity = goodsService.queryObject(id);
		MpGoodsCategoryEntity categoryEntity = goodsService
				.queryCategoryById(entity.getCategoryId().longValue());
		entity.setMpGoodsCategoryEntity(categoryEntity);
		return R.ok().put("entity", entity);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("goods:goods:update")
	@ResponseBody
	public R update(MpGoodsEntity entity) {
		entity.setUserId(getAdminId());
		validData(entity);
		goodsService.update(entity);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("goods:goods:delete")
	@ResponseBody
	public R delete(long[] ids, HttpServletRequest request) {
		validDelete(ids);
		goodsService.deleteBatch(ids);
		return R.ok();
	}
    /**
     * 验证是否可以删除
     */
	private void validDelete(long[] ids){
		String tempId="";
		for(int i=0;i<ids.length;i++){
			int total=goodsService.queryUsingGoods(ids[i]);
			if(total>0){
				tempId+=ids[i]+",";
			}
		}
		if(tempId!=null&&!"".equals(tempId)){
			throw new RRException("id为："+tempId+" 的商品已被使用不能删除");
		}
	}
	/**
	 * 上架 下架
	 */
	@RequestMapping("/auitStatus")
	@ResponseBody
	public R auitStatus(MpGoodsEntity entity) {

		goodsService.auitStatus(entity);
		return R.ok();
	}
}
