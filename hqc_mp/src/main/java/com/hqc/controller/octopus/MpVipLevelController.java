package com.hqc.controller.octopus;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpVipLevelEntity;
import com.hqc.service.MpVipLevelService;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

@RestController
@RequestMapping("/octopus/sys/MpVipLevel")
public class MpVipLevelController {

	@Autowired
	private MpVipLevelService mpVipLevelService;
    /**
     * 查询所有
     * @param page
     * @param checkpage
     * @param limit
     * @param request
     * @return
     */
	@RequestMapping("/list")
	@RequiresPermissions("member:level:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) {
		//获取前台的值，并过滤掉空格
		String mpVipName = request.getParameter("mpVipName").trim();
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
		map.put("vipName", mpVipName);
		map.put("limit", limit);
		
		List<MpVipLevelEntity> list = mpVipLevelService.queryAllList(map);
		int total = mpVipLevelService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}
     /**
      * 添加
      * @param mpMember
      * @param request
      * @return
      * @throws NumberFormatException
      * @throws ParseException
      */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions("member:level:save")
	public R save(MpVipLevelEntity mpMember, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		
		String vipName = request.getParameter("vipName");
		List<MpVipLevelEntity> mp = mpVipLevelService
				.queryMpVipLevelName(vipName);
		if (mp.size() >= 1) {
			throw new RRException("会员等级已被使用,请重新输入");
		}
		// 获取前台页面传过来的积分的最小值及最大值
		String minIntegral = request.getParameter("minIntegral");
		String MaxIntegral = request.getParameter("maxIntegral");
		long min = Long.parseLong(minIntegral);
		long Max = Long.parseLong(MaxIntegral);
		if (min >= Max) {
			throw new RRException("最小积分不能比最大积分的数值大或者相等");
		}
		//做添加时，判断集合中有没有元素
		Map<String, Object> map = new HashMap<>();
		List<MpVipLevelEntity> list = mpVipLevelService.queryAllList(map);
		//当集合元素不为空时，进行以下判断。
		if(list!=null && !list.isEmpty()){
		long maxIngeral = mpVipLevelService.queryMaxIntegral();
		// 把获取到的值与数据库中的最大值进行比较
		if (min < maxIngeral) {
			throw new RRException("最小积分不能比表格中所存在的数值小");
		}
		if (min >= Max) {
			throw new RRException("最小积分不能比最大积分的数值大或者相等");
		}
		}
		// 验证前台页面值
		if (mpMember.getVipName() == null || "".equals(mpMember.getVipName())) {
			throw new RRException("请填写等级名称");
		}
		if (mpMember.getMinIntegral() == null
				|| "".equals(mpMember.getMinIntegral())) {
			throw new RRException("请填写最小积分");
		}
		if (mpMember.getMaxIntegral() == null
				|| "".equals(mpMember.getMaxIntegral())) {
			throw new RRException("请填写最大积分");
		}
		if(mpMember.getNormalIntegralRule() == null
				|| "".equals(mpMember.getNormalIntegralRule())){
			throw new RRException("请填写常规日积分比例");
		}
		if(mpMember.getSpecialIntegralRule() == null
				|| "".equals(mpMember.getSpecialIntegralRule())){
			throw new RRException("请填写特别日积分比例");
		}
		if(mpMember.getIntegralCoefficient() == null
				|| "".equals(mpMember.getIntegralCoefficient())){
			throw new RRException("请填写积分系数");
		}
		if(mpMember.getIconUrl() == null
				|| "".equals(mpMember.getIconUrl())){
			throw new RRException("请上传会员图标");
		}
		mpVipLevelService.save(mpMember);
		return R.ok();
	}
    /**
     * 根据id做查询
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("member:level:info")
	public R info(@PathVariable("id") Long id, HttpServletRequest request) {
		MpVipLevelEntity mpMember = mpVipLevelService.queryMpVipLevelInfoId(id);
		return R.ok().put("mpMember", mpMember);
	}
     /**
      * 修改
      * @param mpMember
      * @param request
      * @param id
      * @return
      * @throws NumberFormatException
      * @throws ParseException
      */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("member:level:update")
	public R update(MpVipLevelEntity mpMember, HttpServletRequest request,
			long id) throws NumberFormatException, ParseException {
		// 验证会员等级是否被使用
		MpVipLevelEntity vipLevel=mpVipLevelService.queryMpVipLevelInfoId(id);
		if(vipLevel.getVipName() != mpMember.getVipName() && !(mpMember.getVipName()).equals(vipLevel.getVipName())){
			List<MpVipLevelEntity> mp = mpVipLevelService
					.queryMpVipLevelName(mpMember.getVipName());
			if(mp.size()>0){
				throw new RRException("会员等级已被占用，请重新输入");	
			}
		}
		// 当修改积分时，把现修改的积分和原积分进行判断
		String minIntegral = request.getParameter("minIntegral");
		String MaxIntegral = request.getParameter("maxIntegral");
		long min = Long.parseLong(minIntegral);// 获取前台传过来的最小积分
		long Max = Long.parseLong(MaxIntegral);// 获取前台传过来的最大积分
		MpVipLevelEntity mpVipLevelEntity = mpVipLevelService
				.queryMpVipLevelInfoId(id);
		long mini = mpVipLevelEntity.getMinIntegral();// 根据id得到最小积分
		long maxi = mpVipLevelEntity.getMaxIntegral();// 根据id得到最大积分
		if (min < mini) {// 把前台传过来的最小积分和根据id做查询得到的积分相比较
			throw new RRException("最小积分不能比本身所存在的值小");
		}
		/*if (Max < maxi) {// 把前台传过来的最大积分和根据id做查询得到的积分相比较
			throw new RRException("最大积分不能比表格中所存在的值小");
		}*/
		if (min >= Max) {// 把前台传过来的最小积分和从前台传过来的最大积分相比较
			throw new RRException("最小积分不能比最大积分大或者相等");
		}
		if(Max>maxi){//把前台传来的值与根据id做查询得到的值想比较
			throw new RRException("最大积分不能大于本身所存在的积分");
		}
		//验证前台页面值
		if (mpMember.getVipName() == null || "".equals(mpMember.getVipName())) {
			throw new RRException("请填写等级名称");
		}
		if (mpMember.getMinIntegral() == null
				|| "".equals(mpMember.getMinIntegral())) {
			throw new RRException("请填写最小积分");
		}
		if (mpMember.getMaxIntegral() == null
				|| "".equals(mpMember.getMaxIntegral())) {
			throw new RRException("请填写最大积分");
		}
		if(mpMember.getNormalIntegralRule() == null
				|| "".equals(mpMember.getNormalIntegralRule())){
			throw new RRException("请填写常规日积分比例");
		}
		if(mpMember.getSpecialIntegralRule() == null
				|| "".equals(mpMember.getSpecialIntegralRule())){
			throw new RRException("请填写特别日积分比例");
		}
		if(mpMember.getIntegralCoefficient() == null
				|| "".equals(mpMember.getIntegralCoefficient())){
			throw new RRException("请填写积分系数");
		}
		if(mpMember.getIconUrl() == null
				|| "".equals(mpMember.getIconUrl())){
			throw new RRException("请上传会员图标");
		}
		mpVipLevelService.update(mpMember);
		
		return R.ok();
	}

	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("member:level:delete")
	public R delete(long[] id) {
		mpVipLevelService.deleteBatch(id);
		return R.ok();
	}

	/**
	 * 判断会员等级是否被占用
	 * @param vipName
	 * @return
	 */
	@RequestMapping("/infoName")
	@ResponseBody
	@RequiresPermissions("member:level:list")
	public R infoPhone(String vipName) {
		List<MpVipLevelEntity> mpMember = mpVipLevelService
				.queryMpVipLevelName(vipName);
		if (mpMember == null || mpMember.size() == 0) {
			return R.ok();
		}

		return R.error("该会员等级已被使用,请重新输入！");
	}

	/**
	 * 查询数据库中积分的最大值
	 * @param request
	 * @return
	 */
	@RequestMapping("/maxIngeral")
	@ResponseBody
	@RequiresPermissions("member:level:list")
	public R maxIngeral(HttpServletRequest request) {
		//判断集合中的元素是否为空。
		Map<String, Object> map = new HashMap<>();
		List<MpVipLevelEntity> list = mpVipLevelService.queryAllList(map);
		long maxIngeral=0;
		if(list!=null && !list.isEmpty()){
			 maxIngeral = mpVipLevelService.queryMaxIntegral();
		}
		return R.ok().put("maxIngeral", maxIngeral);
	}
}
