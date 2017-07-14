package com.hqc.controller.octopus;

import java.text.ParseException;
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

import com.hqc.entity.MpIntegralRecordEntity;
import com.hqc.entity.MpMemberEntity;
import com.hqc.service.MpIntegralRecordService;
import com.hqc.service.MpMemberService;
import com.hqc.util.DateUtils;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

@RestController
@RequestMapping("/octopus/sys/MpIntegralRecord")
public class MpIntegralRecordController extends AbstractController {

	@Resource
	private MpIntegralRecordService mpIntegralRecordService;
	@Resource
	private MpMemberService mpMemberService;

	@RequestMapping("/list")
	@RequiresPermissions("member:integral:list")
	public R list(Integer page, Integer checkpage,Integer limit, HttpServletRequest request) {
		String integralType = request.getParameter("integralType");
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("integralType",Integer.valueOf(integralType));
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		List<MpIntegralRecordEntity> list = mpIntegralRecordService
				.getAllList(map);
		int total = mpIntegralRecordService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
      //添加
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions("member:integral:save")
	public R save(MpIntegralRecordEntity mpIntegralRecordEntity)
			throws NumberFormatException, ParseException {
		// 获取会员列表的值，并把id赋值给memberid
		Map<String, Object> map = new HashMap<>();
		List<MpMemberEntity> list = mpMemberService.queryAllList(map);
		mpIntegralRecordEntity.setMemberId(list.get(0).getId());
		mpIntegralRecordEntity.setCreateTime(DateUtils.getCurrentUnixTime());
		if (mpIntegralRecordEntity.getIntegral() == null) {
			throw new RRException("积分异动数量不能为空");
		}
		mpIntegralRecordEntity.setIntegralType(1);
		mpIntegralRecordService.save(mpIntegralRecordEntity);
		return R.ok();
	}
      //根据id做查询
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("member:integral:info")
	public R info(@PathVariable("id") Long id, HttpServletRequest request) {
		MpIntegralRecordEntity mpIntegralRecordEntity = mpIntegralRecordService
				.querympIntegralRecordInfoId(id);
		return R.ok().put("mpIntegralRecordEntity", mpIntegralRecordEntity);
	}
       //修改
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("member:integral:update")
	public R update(MpIntegralRecordEntity mpIntegralRecordEntity)
			throws NumberFormatException, ParseException {
		if (mpIntegralRecordEntity.getIntegral() == null
				|| "".equals(mpIntegralRecordEntity.getIntegral())) {
			throw new RRException("积分异动数量不能为空");
		}
		mpIntegralRecordService.update(mpIntegralRecordEntity);
		return R.ok();
	}
       //删除
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("member:integral:delete")
	public R delete(long[] id) {
		mpIntegralRecordService.deleteBatch(id);
		return R.ok();
	}
}
