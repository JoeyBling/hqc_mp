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
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hqc.entity.MpMemberEntity;
import com.hqc.entity.MpVipLevelEntity;
import com.hqc.service.MpIntegralRecordService;
import com.hqc.service.MpMemberService;
import com.hqc.service.MpVipLevelService;
import com.hqc.util.DateUtils;
import com.hqc.util.JoeyUtil;
import com.hqc.util.PageUtils;
import com.hqc.util.R;
import com.hqc.util.RRException;

@RestController
@RequestMapping("/octopus/sys/MpMember")
public class MpMemberController {

	@Resource
	private MpMemberService mpMemberService;
	@Resource
	private MpVipLevelService mpVipLevelService;
	@Resource
	private MpIntegralRecordService mpIntegralRecordService;

	/**
	 * 
	 * @param page
	 *            当前页码
	 * @param checkpage
	 *            上一页或者下一页
	 * @param limit
	 *            每页显示的条数
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions("member:member:list")
	public R list(Integer page, Integer checkpage, Integer limit,
			HttpServletRequest request) {
		// 获取前台页面传过来的手机号 会员卡号以及 状态的值并过滤掉空格
		String phone2 = request.getParameter("phone2").trim();
		String cardNo = request.getParameter("cardNo").trim();
		String status = request.getParameter("status");
		if (checkpage != null) {// 判断改值是否为空，点击上一页和下一页都会赋值，其他则不赋值
			if (checkpage == 1) {// 判断用户点击的是下一页或者是上一页
				page = page - 1;// 当点击的是上一页是，当前页面值 -1
			}
			if (checkpage == 2) {
				page = page + 1;// 当点击的是下一页是，当前页面值 +1
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("phone", phone2);
		map.put("cardNo", cardNo);
		map.put("status", Integer.valueOf(status));
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);

		List<MpMemberEntity> list = mpMemberService.queryAllList(map);

		int total = mpMemberService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		return R.ok().put("page", pageUtil);
	}

	/**
	 * 添加
	 * 
	 * @param mpMember
	 * @param request
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions("member:member:save")
	public R save(MpMemberEntity mpMember, HttpServletRequest request)
			throws NumberFormatException, ParseException {
		// 验证前台页面的值
		if (mpMember.getTrueName() == null || "".equals(mpMember.getTrueName())) {
			throw new RRException("请填写会员姓名");
		}
		if (mpMember.getAvatar() == null || "".equals(mpMember.getAvatar())) {
			throw new RRException("请填写会员头像");
		}
		if (mpMember.getPassword() == null || "".equals(mpMember.getPassword())) {
			throw new RRException("请填写会员密码");
		}
		if (mpMember.getPhone() == null || "".equals(mpMember.getPhone())) {
			throw new RRException("请填写手机号码");
		}
		if (mpMember.getTrueName() == null || "".equals(mpMember.getTrueName())) {
			throw new RRException("请填写会员姓名");
		}

		String phone = request.getParameter("phone");
		List<MpMemberEntity> mp = mpMemberService.queryMpMemberInfoPhone(phone);
		if (mp.size() >= 1) {
			throw new RRException("该手机号已被使用,请重新输入");
		}
		/* infoPhone(phone); */
		// 获取会员级别表中的值，并把id的值赋值给VipLevel
		Map<String, Object> map = new HashMap<>();
		List<MpVipLevelEntity> mpVipLevelEntities = mpVipLevelService
				.queryAllList(map);
		// 注册会员送100积分
		long integral = 100l;
		mpMember.setIntegral(integral);
		// 把会员级别表的id放入VipLevel中
		mpMember.setVipLevel(mpVipLevelEntities.get(0).getId().intValue());
		mpMember.setCreateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		mpMember.setUpdateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		// 获取前台传过来的会员生日，当时间不为空时做添加
			String birthday = request.getParameter("birth");
			if(birthday!=null && !"".equals(birthday)){
				String format = "yyyy-MM-dd HH:mm";
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				long date = JoeyUtil.stampDate(sdf.parse(birthday),
						DateUtils.DATE_TIME_PATTERN);
				mpMember.setBirthday(date);
				 }
		
		mpMember.setStatus(1);
		mpMemberService.save(mpMember);
		return R.ok();
	}

	/**
	 * 根据id做查询
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/info/{id}")
	@ResponseBody
	@RequiresPermissions("member:member:info")
	public R info(@PathVariable("id") Long id, HttpServletRequest request) {
		MpMemberEntity mpMember = mpMemberService.queryMpMemberInfoId(id);
		return R.ok().put("mpMember", mpMember);
	}

	/**
	 * 验证手机号是否被占用
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/infoPhone")
	@ResponseBody
	@RequiresPermissions("member:member:save")
	public R infoPhone(String phone) {
		List<MpMemberEntity> mpMember = mpMemberService
				.queryMpMemberInfoPhone(phone);
		if (mpMember == null || mpMember.size() == 0) {
			return R.ok();
		}

		return R.error("该手机号已被使用,请重新输入！");
	}

	/**
	 * 修改
	 * 
	 * @param mpMember
	 * @return
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("member:member:update")
	public R update(MpMemberEntity mpMember,long id,HttpServletRequest request) throws NumberFormatException,
			ParseException {
		// 验证前台页面的值
		if (mpMember.getPhone() == null || "".equals(mpMember.getPhone())) {
			throw new RRException("请填写手机号码");
		}
		if (mpMember.getTrueName() == null || "".equals(mpMember.getTrueName())) {
			throw new RRException("请填写会员姓名");
		}
		if (mpMember.getAvatar() == null || "".equals(mpMember.getAvatar())) {
			throw new RRException("请填写会员头像");
		}
		
		mpMember.setUpdateTime(JoeyUtil.stampDate(new Date(),
				DateUtils.DATE_TIME_PATTERN));
		//获取前台页面传过来的值
		String vipName = request.getParameter("vip");
		String integral = request.getParameter("integral");
		long gral=Long.parseLong(integral);
		//根据等级名称做查询
		MpVipLevelEntity entity=mpVipLevelService.queryVipLevel(vipName);
		//总积分与最小积分做判断
		if(entity==null){
			throw new RRException("该会员等级不存在，请重新输入");
		}else{
			if(gral<entity.getMinIntegral()){
				throw new RRException("积分不在该等级范围内，无法修改");
			}	
		}
		long id1=entity.getId();
		mpMember.setVipLevel(new Long(id1).intValue());
		//根据id做查询，把前台的值与后台的值相比较，当两个值不相等时，会员生日无法修改
		MpMemberEntity memberEntity=mpMemberService.queryMpMemberInfoId(id);
		Long bir=memberEntity.getBirthday(); 
		String birthday = request.getParameter("birth");
		 String format = "yyyy-MM-dd HH:mm";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Long date = JoeyUtil.stampDate(sdf.parse(birthday),
					DateUtils.DATE_TIME_PATTERN); 
			if(bir != null){
				if(date.longValue()==bir.longValue()){
					mpMember.setBirthday(date);
				}else if(bir==0){
					mpMember.setBirthday(date);	
				}
				else{
					 throw new RRException("该会员生日无法修改"); 
				}
			}if(birthday!=null && !"".equals(birthday)){//当前台传过来的值不为空时，做添加
				mpMember.setBirthday(date);
				 }
			
		mpMemberService.update(mpMember);
		return R.ok();
	}

	/**
	 * 根据id做删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("member:member:delete")
	public R delete(long[] id) {
		mpMemberService.deleteBatch(id);
		return R.ok();
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public R updatePassword(HttpServletRequest request) {
		// 获取前台也面对值
		String idPassword = request.getParameter("id");
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		String secPassword = request.getParameter("secPassword");
		long id = Long.parseLong(idPassword);
		// 非空判断
		if (StringUtils.isBlank(oldPassword)) {
			return R.error("请填写原密码");
		}
		if (StringUtils.isBlank(newPassword)) {
			return R.error("请填写新密码");
		}
		if (StringUtils.isBlank(secPassword)) {
			return R.error("请确认密码");
		}
		if (!newPassword.equals(secPassword)) {
			return R.error("两次密码输入不同");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("oldPassword", new Sha256Hash(oldPassword).toHex());
		map.put("newPassword", new Sha256Hash(newPassword).toHex());

		int count = this.mpMemberService.updatePassword(map);
		if (count == 0) {
			return R.error("原密码错误");
		}
		return R.ok();
	}
}
